package com.dutact.web.features.event.student.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "StudentEventDto")
public class EventDto {
    @JsonProperty("id")
    private Integer id;

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

    @Nullable
    @JsonProperty("registeredAt")
    @Schema(description = "The date and time when the student registered for the event, " +
            "null if the student has not registered for the event")
    private LocalDateTime registeredAt;

    @JsonProperty("coverPhotoUrl")
    private String coverPhotoUrl;

    @JsonProperty("organizer")
    private OrganizerDto organizer;
}