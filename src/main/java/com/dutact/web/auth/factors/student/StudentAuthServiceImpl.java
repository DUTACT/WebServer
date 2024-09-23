package com.dutact.web.auth.factors.student;

import com.dutact.web.auth.UserCredential;
import com.dutact.web.auth.dto.ConfirmDto;
import com.dutact.web.auth.dto.LoginDto;
import com.dutact.web.auth.dto.ResponseToken;
import com.dutact.web.auth.dto.student.StudentConfirmResetPasswordDto;
import com.dutact.web.auth.dto.student.StudentRegisterDto;
import com.dutact.web.auth.dto.student.StudentResetPasswordDto;
import com.dutact.web.auth.exception.InvalidLoginCredentialsException;
import com.dutact.web.auth.exception.OtpException;
import com.dutact.web.auth.exception.UsernameOrEmailAlreadyExistException;
import com.dutact.web.auth.exception.UsernameOrEmailNotExistException;
import com.dutact.web.auth.otp.OtpService;
import com.dutact.web.auth.token.jwt.JWTProcessor;
import com.dutact.web.core.entities.Student;
import com.dutact.web.core.repositories.StudentRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentAuthServiceImpl implements StudentAuthService {
    private final StudentRepository studentRepository;

    private final JWTProcessor jwtProcessor;

    private final PasswordEncoder passwordEncoder;

    private final OtpService otpService;
    private final long jwtLifespan;

    public StudentAuthServiceImpl(@Value("${auth.jwt.lifespan}") long jwtLifespan,
                                  JWTProcessor jwtProcessor,
                                  StudentRepository studentRepository,
                                  PasswordEncoder passwordEncoder,
                                  OtpService otpService) {
        this.jwtLifespan = jwtLifespan;
        this.studentRepository = studentRepository;
        this.jwtProcessor = jwtProcessor;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
    }

    @Override
    public ResponseToken login(LoginDto loginDto) throws InvalidLoginCredentialsException {
        String email = loginDto.getUsername();
        String password = loginDto.getPassword();

        UserCredential userCredential = studentRepository.findByUsername(email)
                .orElseThrow(InvalidLoginCredentialsException::new);

        if (!passwordEncoder.matches(password, userCredential.getPassword())) {
            throw new InvalidLoginCredentialsException();
        }

        String token = jwtProcessor.getBuilder()
                .withSubject(email)
                .withClaim("expiredAt", System.currentTimeMillis() + jwtLifespan)
                .withScopes(List.of("ROLE_"+ userCredential.getRole()))
                .build();

        ResponseToken responseToken = new ResponseToken();
        responseToken.setAccessToken(token);

        return responseToken;
    }

    @Override
    public void register(StudentRegisterDto registerDTO) throws UsernameOrEmailAlreadyExistException, MessagingException {
        if (studentRepository.existsByUsername(registerDTO.getEmail())) {
            throw new UsernameOrEmailAlreadyExistException();
        }

        Student student = new Student();
        student.setUsername(registerDTO.getEmail());
        student.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        student.setName(registerDTO.getFullName());
        student.setMajor(registerDTO.getFaculty());
        student.setEnabled(false);
        studentRepository.save(student);

        otpService.sendOtp(student.getUsername());
    }

    @Override
    public void confirmRegistration(ConfirmDto verificationTokenDto) throws OtpException {
        boolean isOtpValid = otpService.verifyOtp(verificationTokenDto.getEmail(), verificationTokenDto.getOtp());
        if (!isOtpValid) {
            throw new OtpException();
        }
        Student student = studentRepository.findByUsername(verificationTokenDto.getEmail()).get();

        student.setEnabled(true);
        studentRepository.save(student);
    }

    @Override
    public void resetPassword(StudentResetPasswordDto resetPasswordDto) throws UsernameOrEmailNotExistException, MessagingException {
        Student student = studentRepository.findByUsername(resetPasswordDto.getEmail())
                .orElseThrow(UsernameOrEmailNotExistException::new);

        otpService.sendOtp(student.getUsername());
    }

    @Override
    public void resetNewPassword(StudentConfirmResetPasswordDto resetPasswordDto) throws OtpException {
        boolean isOtpValid = otpService.verifyOtp(resetPasswordDto.getEmail(), resetPasswordDto.getOtp());
        if (!isOtpValid) {
            throw new OtpException();
        }
        Student student = studentRepository.findByUsername(resetPasswordDto.getEmail()).get();

        student.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
        studentRepository.save(student);
    }
}
