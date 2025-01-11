package com.dutact.web.dto.auth.student;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResetPasswordDto {
    @NotBlank
    private String email;
}
