package com.dutact.web.dto.profile.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
@Schema(name = "StudentProfileDto")
public class StudentProfileDto {
    @JsonProperty("studentEmail")
    private String studentEmail;

    @JsonProperty("name")
    private String fullName;

    @Nullable
    @Schema(nullable = true)
    @JsonProperty("phone")
    private String phone;

    @Nullable
    @Schema(nullable = true)
    @JsonProperty("faculty")
    private String faculty;

    @Nullable
    @Schema(nullable = true)
    @JsonProperty("avatarUrl")
    private String avatarUrl;

    @Nullable
    @Schema(nullable = true)
    @JsonProperty("address")
    private String address;

    @Nullable
    @Schema(nullable = true)
    @JsonProperty("className")
    private String className;
}
