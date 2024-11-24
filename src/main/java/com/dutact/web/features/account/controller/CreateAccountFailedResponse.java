package com.dutact.web.features.account.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateAccountFailedResponse {
    public static final String USERNAME_ALREADY_EXISTS = "username_already_exists";

    @Schema(allowableValues = {USERNAME_ALREADY_EXISTS})
    @JsonProperty("status")
    private String status;
}
