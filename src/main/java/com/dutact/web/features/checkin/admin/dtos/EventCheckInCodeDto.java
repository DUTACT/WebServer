package com.dutact.web.features.checkin.admin.dtos;

import com.dutact.web.core.entities.checkincode.CheckInLocation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EventCheckInCodeDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("eventId")
    private Integer eventId;

    @JsonProperty("startAt")
    private LocalDateTime startAt;

    @JsonProperty("endAt")
    private LocalDateTime endAt;

    @Nullable
    @JsonProperty("location")
    private CheckInLocation location;
}
