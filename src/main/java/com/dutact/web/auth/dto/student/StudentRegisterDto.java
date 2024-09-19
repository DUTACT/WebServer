package com.dutact.web.auth.dto.student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRegisterDto {
    @NotEmpty(message = "Full name is required")
    private String fullName;

    @NotEmpty(message = "faculty is required")
    private String faculty;

    @NotEmpty(message = "Student email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
