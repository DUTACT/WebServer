package com.dutact.web.features.event.student.dtos;

import com.dutact.web.core.entities.event.EventStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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

    @JsonProperty("followerNumber")
    private Integer followerNumber;

    @JsonProperty("registerNumber")
    private Integer registerNumber;

    @JsonProperty("startRegistrationAt")
    private LocalDateTime startRegistrationAt;

    @JsonProperty("endRegistrationAt")
    private LocalDateTime endRegistrationAt;

    @Nullable
    @JsonProperty("registeredAt")
    @Schema(description = "The date and time when the student registered for the event, " +
            "null if the student has not registered for the event")
    private LocalDateTime registeredAt;

    @Nullable
    @JsonProperty("followedAt")
    @Schema(description = "The date and time when the student followed the event, " +
            "null if the student has not followed the event")
    private LocalDateTime followedAt;

    @JsonProperty("status")
    private EventStatus status;

    @JsonProperty("coverPhotoUrls")
    private List<String> coverPhotoUrls;

    @JsonProperty("organizer")
    private OrganizerDto organizer;
}