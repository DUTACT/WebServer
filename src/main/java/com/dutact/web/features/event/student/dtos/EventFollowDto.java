package com.dutact.web.features.event.student.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "StudentEventFollwedDto")
public class EventFollowDto {
    @JsonProperty("studentId")
    private Integer studentId;

    @JsonProperty("eventId")
    private Integer eventId;

    @JsonProperty("followAt")
    private String followAt;
}
