package com.dutact.web.service.auth;


import com.dutact.web.dto.auth.ConfirmDto;
import com.dutact.web.dto.auth.student.StudentConfirmResetPasswordDto;
import com.dutact.web.dto.auth.student.StudentRegisterDto;
import com.dutact.web.dto.auth.student.StudentResetPasswordDto;
import com.dutact.web.service.auth.exception.OtpException;
import com.dutact.web.service.auth.exception.UsernameOrEmailAlreadyExistException;
import com.dutact.web.service.auth.exception.UsernameOrEmailNotExistException;
import jakarta.mail.MessagingException;

public interface StudentAuthService {
    void register(StudentRegisterDto registerDTO) throws MessagingException, UsernameOrEmailAlreadyExistException;

    void resendOtp(String email) throws MessagingException;

    void confirmRegistration(ConfirmDto verificationTokenDto) throws OtpException;

    void resetPassword(StudentResetPasswordDto resetPasswordDto) throws UsernameOrEmailNotExistException, MessagingException;

    void resetNewPassword(StudentConfirmResetPasswordDto confirmNewPasswordTokenDto) throws OtpException;
}
