package com.dutact.web.auth.factors.student;

import com.dutact.web.auth.UserCredential;
import com.dutact.web.auth.dto.ConfirmTokenDto;
import com.dutact.web.auth.dto.LoginDto;
import com.dutact.web.auth.dto.ResponseToken;
import com.dutact.web.auth.dto.student.StudentConfirmResetPasswordDto;
import com.dutact.web.auth.dto.student.StudentRegisterDto;
import com.dutact.web.auth.dto.student.StudentResetPasswordDto;
import com.dutact.web.auth.exception.InvalidLoginCredentialsException;
import com.dutact.web.auth.exception.UsernameOrEmailAlreadyExistException;
import com.dutact.web.auth.exception.UsernameOrEmailNotExistException;
import com.dutact.web.auth.token.jwt.InvalidJWTException;
import com.dutact.web.auth.token.jwt.JWTProcessor;
import com.dutact.web.auth.token.jwt.VerifiedJWT;
import com.dutact.web.common.email.EmailSenderService;
import com.dutact.web.common.email.EmailTemplateService;
import com.dutact.web.core.entities.Student;
import com.dutact.web.core.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentAuthServiceImpl implements StudentAuthService {
    private final StudentRepository studentRepository;

    private final JWTProcessor jwtProcessor;

    private final PasswordEncoder passwordEncoder;

    private final EmailTemplateService emailTemplateService;

    private final EmailSenderService emailSenderService;

    private final long verificationDuration;

    private String appUrl;

    public StudentAuthServiceImpl(@Value("${auth.jwt.verification-duration}") long verificationDuration,
                                  JWTProcessor jwtProcessor,
                                  StudentRepository studentRepository,
                                  PasswordEncoder passwordEncoder,
                                  EmailTemplateService emailTemplateService,
                                  EmailSenderService emailSenderService,
                                  @Value("${app.url}") String appUrl) {
        this.verificationDuration = verificationDuration;
        this.studentRepository = studentRepository;
        this.jwtProcessor = jwtProcessor;
        this.passwordEncoder = passwordEncoder;
        this.emailTemplateService = emailTemplateService;
        this.emailSenderService = emailSenderService;
        this.appUrl = appUrl;
    }

    @Override
    public ResponseToken login(LoginDto loginDto) {
        String email = loginDto.getUsername();
        String password = loginDto.getPassword();

        UserCredential userCredential = studentRepository.findByUsername(email)
                .orElseThrow(InvalidLoginCredentialsException::new);

        if (!passwordEncoder.matches(password, userCredential.getPassword())) {
            throw new InvalidLoginCredentialsException();
        }

        String token = jwtProcessor.getBuilder()
                .withSubject(email)
                .withScopes(List.of("ROLE_"+ userCredential.getRole()))
                .build();

        ResponseToken responseToken = new ResponseToken();
        responseToken.setAccessToken(token);

        return responseToken;
    }

    @Override
    public void register(StudentRegisterDto registerDTO) {
        String token = jwtProcessor.getBuilder()
                .withSubject(registerDTO.getEmail())
                .withClaim("expiredAt", System.currentTimeMillis() + verificationDuration)
                .withClaim("hashedPassword", passwordEncoder.encode(registerDTO.getPassword()))
                .withClaim("fullName", registerDTO.getFullName())
                .withClaim("faculty", registerDTO.getFaculty())
                .build();

        if (studentRepository.existsByUsername(registerDTO.getEmail())) {
            throw new UsernameOrEmailAlreadyExistException();
        }

        String emailContent = emailTemplateService.generateEmailContent("student-confirmation", Map.of(
                "appUrl", appUrl,
                "token", token
        ));
        emailSenderService.sendEmail(registerDTO.getEmail(), "Kích hoạt tài khoản DUTACT của bạn", emailContent);
    }

    @Override
    public void confirmRegistration(ConfirmTokenDto verificationTokenDto) {
        VerifiedJWT verifiedJWT = jwtProcessor.getVerifiedJWT(verificationTokenDto.getToken());

        Optional<Long> expiredAt = verifiedJWT.getClaim("expiredAt", Long.class);
        if (expiredAt.isEmpty() || expiredAt.get() < System.currentTimeMillis()) {
            throw new InvalidJWTException("Token expired");
        }

        String email = verifiedJWT.getUsername();
        if (studentRepository.existsByUsername(email)) {
            throw new UsernameOrEmailAlreadyExistException();
        }
        String hashedPassword = verifiedJWT.getClaim("hashedPassword", String.class)
                .orElseThrow(() -> new InvalidJWTException("Hashed password not found"));
        String fullName = verifiedJWT.getClaim("fullName", String.class)
                .orElseThrow(() -> new InvalidJWTException("Full name not found"));
        String faculty = verifiedJWT.getClaim("faculty", String.class)
                .orElseThrow(() -> new InvalidJWTException("Faculty not found"));

        Student student = new Student();
        student.setName(fullName);
        student.setUsername(email);
        student.setPassword(hashedPassword);
        student.setMajor(faculty);

        student.setEnabled(true);
        studentRepository.save(student);
    }

    @Override
    public void resetPassword(StudentResetPasswordDto resetPasswordDto) {
        Student student = studentRepository.findByUsername(resetPasswordDto.getEmail())
                .orElseThrow(UsernameOrEmailNotExistException::new);

        String token = jwtProcessor.getBuilder()
                .withSubject(student.getUsername())
                .withClaim("expiredAt", System.currentTimeMillis() + verificationDuration)
                .build();

        String emailContent = emailTemplateService.generateEmailContent("reset-password-email", Map.of(
                "appUrl", appUrl,
                "email", resetPasswordDto.getEmail(),
                "token", token
        ));

        emailSenderService.sendEmail(resetPasswordDto.getEmail(), "Reset mật khẩu", emailContent);
    }

    @Override
    public void resetNewPassword(StudentConfirmResetPasswordDto tokenDto) {
        VerifiedJWT verifiedJWT = jwtProcessor.getVerifiedJWT(tokenDto.getToken());

        Optional<Long> expiredAt = verifiedJWT.getClaim("expiredAt", Long.class);
        if (expiredAt.isEmpty() || expiredAt.get() < System.currentTimeMillis()) {
            throw new InvalidJWTException("Token expired");
        }

        Student student = studentRepository.findByUsername(tokenDto.getEmail())
                .orElseThrow(UsernameOrEmailNotExistException::new);

        if (!verifiedJWT.getUsername().equals(tokenDto.getEmail())) {
            throw new InvalidJWTException("Invalid token");
        }

        student.setPassword(passwordEncoder.encode(tokenDto.getNewPassword()));
        studentRepository.save(student);
    }
}
