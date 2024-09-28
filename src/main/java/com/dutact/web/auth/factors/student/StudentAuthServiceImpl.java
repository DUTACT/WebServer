package com.dutact.web.auth.factors.student;

import com.dutact.web.auth.dto.ConfirmDto;
import com.dutact.web.auth.dto.student.StudentConfirmResetPasswordDto;
import com.dutact.web.auth.dto.student.StudentRegisterDto;
import com.dutact.web.auth.dto.student.StudentResetPasswordDto;
import com.dutact.web.auth.exception.OtpException;
import com.dutact.web.auth.exception.UsernameOrEmailAlreadyExistException;
import com.dutact.web.auth.exception.UsernameOrEmailNotExistException;
import com.dutact.web.auth.factors.Role;
import com.dutact.web.auth.otp.OtpService;
import com.dutact.web.auth.token.jwt.JWTProcessor;
import com.dutact.web.core.entities.Student;
import com.dutact.web.core.repositories.StudentRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentAuthServiceImpl implements StudentAuthService {
    private final StudentRepository studentRepository;

    private final PasswordEncoder passwordEncoder;

    private final OtpService otpService;

    public StudentAuthServiceImpl(StudentRepository studentRepository,
                                  PasswordEncoder passwordEncoder,
                                  OtpService otpService) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
    }

    @Override
    public void register(StudentRegisterDto registerDTO) throws UsernameOrEmailAlreadyExistException, MessagingException {
        if (studentRepository.existsByUsername(registerDTO.getEmail())) {
            throw new UsernameOrEmailAlreadyExistException();
        }

        Student student = new Student();
        student.setUsername(registerDTO.getEmail());
        student.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        student.setFullName(registerDTO.getFullName());
        student.setRole(Role.STUDENT);
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
