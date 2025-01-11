package com.dutact.web.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseToken {
    @NotBlank
    private String accessToken;
}
