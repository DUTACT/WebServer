package com.dutact.web.features.event.admin.dtos.event;

import com.dutact.web.core.entities.eventchange.details.EventChangeDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventChangeDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("details")
    private EventChangeDetails details;

    @JsonProperty("changedAt")
    private LocalDateTime changedAt;
}
