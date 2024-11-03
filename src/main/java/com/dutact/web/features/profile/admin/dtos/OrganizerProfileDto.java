package com.dutact.web.features.profile.admin.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "organizerProfileDto")
public class OrganizerProfileDto {
    @JsonProperty("studentEmail")
    private String studentEmail;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("faculty")
    private String faculty;

    @JsonProperty("avatarUrl")
    private String avatarUrl;

    @JsonProperty("address")
    private String address;

    @JsonProperty("className")
    private String className;
}
