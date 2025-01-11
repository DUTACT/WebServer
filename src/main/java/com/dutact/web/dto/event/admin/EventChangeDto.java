package com.dutact.web.dto.event.admin;

import com.dutact.web.data.entity.eventchange.details.EventChangeDetails;
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
