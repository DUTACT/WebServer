package com.dutact.web.features.event.admin.dtos.event;

import com.dutact.web.core.entities.event.EventStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(name = "AdminEventDto")
public class EventDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("content")
    private String content;

    @JsonProperty("createdAt")
    private String createdAt;

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

    @JsonProperty("coverPhotoUrls")
    private List<String> coverPhotoUrls;

    @JsonProperty("status")
    private EventStatus status;

    @JsonProperty("organizer")
    private OrganizerDto organizer;
}
