package com.dutact.web.service.auth;

import com.dutact.web.data.entity.Student;
import com.dutact.web.data.entity.auth.Role;
import com.dutact.web.data.repository.StudentRepository;
import com.dutact.web.dto.auth.ConfirmDto;
import com.dutact.web.dto.auth.student.StudentConfirmResetPasswordDto;
import com.dutact.web.dto.auth.student.StudentRegisterDto;
import com.dutact.web.dto.auth.student.StudentResetPasswordDto;
import com.dutact.web.service.auth.exception.OtpException;
import com.dutact.web.service.auth.exception.UsernameOrEmailAlreadyExistException;
import com.dutact.web.service.auth.exception.UsernameOrEmailNotExistException;
import jakarta.mail.MessagingException;
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
    public void register(StudentRegisterDto registerDTO) throws MessagingException, UsernameOrEmailAlreadyExistException {
        if (studentRepository.existsByUsername(registerDTO.getEmail())) {
            if (studentRepository.findByUsername(registerDTO.getEmail()).get().isEnabled()) {
                throw new UsernameOrEmailAlreadyExistException();
            }
            otpService.sendOtp(registerDTO.getEmail());
            return;
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
    public void resendOtp(String email) throws MessagingException {
        otpService.sendOtp(email);
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
