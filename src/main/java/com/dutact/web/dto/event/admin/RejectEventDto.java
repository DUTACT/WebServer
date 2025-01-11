package com.dutact.web.dto.event.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RejectEventDto {
    @JsonProperty("reason")
    private String reason;
}
