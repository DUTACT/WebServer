package com.dutact.web.auth.dto.student;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResetPasswordDto {
    @NotEmpty(message = "Student email is required")
    private String email;
}
