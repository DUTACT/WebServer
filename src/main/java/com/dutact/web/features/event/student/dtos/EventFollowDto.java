package com.dutact.web.features.event.student.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "StudentEventFollowDto")
public class EventFollowDto {
    @JsonProperty("studentId")
    private Integer studentId;

    @JsonProperty("eventId")
    private Integer eventId;

    @JsonProperty("followedAt")
    private String followedAt;
}
