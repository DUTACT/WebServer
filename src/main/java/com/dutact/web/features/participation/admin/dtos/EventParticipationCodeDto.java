package com.dutact.web.features.participation.admin.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EventParticipationCodeDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("eventId")
    private Integer eventId;

    @JsonProperty("startAt")
    private LocalDateTime startAt;

    @JsonProperty("endAt")
    private LocalDateTime endAt;
}
