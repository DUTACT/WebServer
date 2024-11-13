package com.dutact.web.features.event.student.dtos;

import com.dutact.web.core.entities.eventregistration.participationcert.ParticipationCertificateStatus;
import com.dutact.web.features.checkin.admin.dtos.CheckInHistoryDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(name = "StudentEventFollowDetailsDto")
public class EventFollowDetailsDto {
    @JsonProperty("studentId")
    private Integer studentId;

    @JsonProperty("event")
    private EventDto event;

    @JsonProperty("followAt")
    private LocalDateTime followAt;
}
