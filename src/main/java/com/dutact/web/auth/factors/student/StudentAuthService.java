package com.dutact.web.auth.factors.student;


import com.dutact.web.auth.dto.ConfirmDto;
import com.dutact.web.auth.dto.student.StudentConfirmResetPasswordDto;
import com.dutact.web.auth.dto.student.StudentRegisterDto;
import com.dutact.web.auth.dto.student.StudentResetPasswordDto;
import com.dutact.web.auth.exception.OtpException;
import com.dutact.web.auth.exception.UsernameOrEmailNotExistException;
import jakarta.mail.MessagingException;

public interface StudentAuthService {
    void register(StudentRegisterDto registerDTO) throws MessagingException;
    void confirmRegistration(ConfirmDto verificationTokenDto) throws OtpException;
    void resetPassword(StudentResetPasswordDto resetPasswordDto) throws UsernameOrEmailNotExistException, MessagingException;
    void resetNewPassword(StudentConfirmResetPasswordDto confirmNewPasswordTokenDto) throws OtpException;
}
