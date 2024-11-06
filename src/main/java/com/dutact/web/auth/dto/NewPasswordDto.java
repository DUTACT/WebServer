package com.dutact.web.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPasswordDto {
    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}
