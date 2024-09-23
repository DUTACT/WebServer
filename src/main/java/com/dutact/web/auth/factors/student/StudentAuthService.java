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
import jakarta.mail.MessagingException;

public interface StudentAuthService {
    ResponseToken login(LoginDto loginDto) throws InvalidLoginCredentialsException;
    void register(StudentRegisterDto registerDTO) throws UsernameOrEmailAlreadyExistException, MessagingException;
    void confirmRegistration(ConfirmDto verificationTokenDto) throws OtpException;
    void resetPassword(StudentResetPasswordDto resetPasswordDto) throws UsernameOrEmailNotExistException, MessagingException;
    void resetNewPassword(StudentConfirmResetPasswordDto confirmNewPasswordTokenDto) throws OtpException;
}
