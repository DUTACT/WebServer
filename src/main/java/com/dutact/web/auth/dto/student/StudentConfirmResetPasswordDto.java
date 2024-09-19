package com.dutact.web.auth.dto.student;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentConfirmResetPasswordDto {
    @NotEmpty(message = "Student email is required")
    private String email;

    @NotEmpty(message = "New password is required")
    private String newPassword;

    @NotEmpty(message = "Token is required")
    private String token;
}
