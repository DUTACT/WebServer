package com.dutact.web.features.checkin.student.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StudentRegistrationDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("coverPhotoUrl")
    private String coverPhotoUrl;

    @JsonProperty("coverPhotoUrls")
    private List<String> coverPhotoUrls;

    @JsonProperty("startAt")
    private LocalDateTime startAt;

    @JsonProperty("endAt")
    private LocalDateTime endAt;

    @JsonProperty("registeredAt")
    private LocalDateTime registeredAt;
} 