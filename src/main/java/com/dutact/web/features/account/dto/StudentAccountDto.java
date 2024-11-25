package com.dutact.web.features.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StudentAccountDto extends AccountDto {
    @JsonProperty("fullName")
    private String fullName;

    @Nullable
    @JsonProperty("phone")
    private String phone;

    @Nullable
    @JsonProperty("faculty")
    private String faculty;

    @Nullable
    @JsonProperty("address")
    private String address;

    @Nullable
    @JsonProperty("className")
    private String className;

    @Nullable
    @JsonProperty("avatarUrl")
    private String avatarUrl;
}