package com.dutact.web.features.checkin.student.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentRegistrationDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("coverPhotoUrl")
    private String coverPhotoUrl;

    @JsonProperty("startAt")
    private LocalDateTime startAt;

    @JsonProperty("endAt")
    private LocalDateTime endAt;

    @JsonProperty("registeredAt")
    private LocalDateTime registeredAt;
} 