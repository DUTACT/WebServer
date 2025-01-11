package com.dutact.web.dto.checkin.admin;

import com.dutact.web.data.entity.checkincode.CheckInLocation;
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
