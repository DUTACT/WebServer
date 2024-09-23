package com.dutact.web.auth.factors.student;

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
import com.dutact.web.common.message.ErrorMessage;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentAuthController {
    private final StudentAuthService studentAuthService;

    public StudentAuthController(StudentAuthService studentAuthService) {
        this.studentAuthService = studentAuthService;
    }

    @PostMapping("/api/student/login")
    public ResponseEntity<Object> postLogin(@RequestBody @Valid LoginDto loginDto) {
        try {
            ResponseToken responseToken = studentAuthService.login(loginDto);
            return ResponseEntity.ok(responseToken);
        }
        catch (InvalidLoginCredentialsException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/api/student/register")
    public ResponseEntity<Object> postRegister(@RequestBody @Valid StudentRegisterDto registerDto) {
        try {
            studentAuthService.register(registerDto);
        } catch (UsernameOrEmailAlreadyExistException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        catch (MessagingException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/api/student/confirm-registration")
    public ResponseEntity<Object> confirmRegistration(@RequestBody @Valid ConfirmDto confirmDto) {
        try {
            studentAuthService.confirmRegistration(confirmDto);
        }
        catch (OtpException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/student/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody @Valid StudentResetPasswordDto resetPasswordDto) {
        try {
            studentAuthService.resetPassword(resetPasswordDto);
        }
        catch (UsernameOrEmailNotExistException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (MessagingException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/api/student/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody @Valid StudentConfirmResetPasswordDto confirmResetPasswordDto) {
        try {
            studentAuthService.resetNewPassword(confirmResetPasswordDto);
        }
        catch (OtpException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.noContent().build();
    }
}
