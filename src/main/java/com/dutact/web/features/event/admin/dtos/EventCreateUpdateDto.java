package com.dutact.web.features.event.admin.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventCreateUpdateDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("content")
    private String content;

    @JsonProperty("startAt")
    private LocalDateTime startAt;

    @JsonProperty("endAt")
    private LocalDateTime endAt;

    @JsonProperty("startRegistrationAt")
    private LocalDateTime startRegistrationAt;

    @JsonProperty("endRegistrationAt")
    private LocalDateTime endRegistrationAt;

    @JsonProperty("coverPhotoUrl")
    private String coverPhotoUrl;
}