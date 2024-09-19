package com.dutact.web.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseToken {
    @NotEmpty(message = "Access token is required")
    private String accessToken;
}
