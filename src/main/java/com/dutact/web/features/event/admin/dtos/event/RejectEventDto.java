package com.dutact.web.features.event.admin.dtos.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RejectEventDto {
    @JsonProperty("reason")
    private String reason;
}
