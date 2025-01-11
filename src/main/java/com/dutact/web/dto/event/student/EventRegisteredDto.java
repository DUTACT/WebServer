package com.dutact.web.dto.event.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "StudentEventRegisteredDto")
public class EventRegisteredDto {
    @JsonProperty("studentId")
    private Integer studentId;

    @JsonProperty("eventId")
    private Integer eventId;

    @JsonProperty("registeredAt")
    private String registeredAt;
}
