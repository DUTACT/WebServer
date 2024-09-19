package com.dutact.web.auth.factors.student;


import com.dutact.web.auth.dto.ConfirmTokenDto;
import com.dutact.web.auth.dto.LoginDto;
import com.dutact.web.auth.dto.ResponseToken;
import com.dutact.web.auth.dto.student.StudentConfirmResetPasswordDto;
import com.dutact.web.auth.dto.student.StudentRegisterDto;
import com.dutact.web.auth.dto.student.StudentResetPasswordDto;

public interface StudentAuthService {
    ResponseToken login(LoginDto loginDto);
    void register(StudentRegisterDto registerDTO);
    void confirmRegistration(ConfirmTokenDto verificationTokenDto);
    void resetPassword(StudentResetPasswordDto resetPasswordDto);
    void resetNewPassword(StudentConfirmResetPasswordDto confirmNewPasswordTokenDto);
}
