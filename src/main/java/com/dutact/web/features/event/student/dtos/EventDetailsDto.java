package com.dutact.web.features.event.student.dtos;

import com.dutact.web.core.entities.eventregistration.participationcert.ParticipationCertificateStatus;
import com.dutact.web.features.checkin.admin.dtos.CheckInHistoryDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "StudentEventRegisteredDetailsDto")
public class EventDetailsDto {
    @JsonProperty("studentId")
    private Integer studentId;

    @JsonProperty("event")
    private EventDto event;

    @JsonProperty("totalCheckIn")
    private Integer totalCheckIn;

    @JsonProperty("certificateStatus")
    private ParticipationCertificateStatus certificateStatus;

    @JsonProperty("checkIns")
    private List<CheckInHistoryDto> checkIns;
}
