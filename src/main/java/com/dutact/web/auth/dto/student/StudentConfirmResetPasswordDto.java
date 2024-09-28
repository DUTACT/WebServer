package com.dutact.web.auth.dto.student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentConfirmResetPasswordDto {
    @NotBlank
    private String email;

    @NotBlank
    private String newPassword;

    @NotNull
    private Long otp;
}
