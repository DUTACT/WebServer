package com.dutact.web.features.checkin.admin.dtos;

import com.dutact.web.core.entities.participationcert.ParticipationCertificateStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CheckInPreviewDto {
    @JsonProperty("studentId")
    private Integer studentId;

    @JsonProperty("studentName")
    private String studentName;

    @JsonProperty("totalCheckIn")
    private Integer totalCheckIn;

    @JsonProperty("certificateStatus")
    private ParticipationCertificateStatus certificateStatus;
}
