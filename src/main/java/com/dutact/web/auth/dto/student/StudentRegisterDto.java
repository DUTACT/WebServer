package com.dutact.web.auth.dto.student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRegisterDto {
    @NotBlank
    private String fullName;

    @NotBlank
    @Pattern(regexp = "^[0-9]{9}@sv1\\.dut\\.udn\\.vn$", message = "Email must be 9 digits followed by '@sv1.dut.udn.vn'")
    private String email;

    @NotBlank
    private String password;
}
