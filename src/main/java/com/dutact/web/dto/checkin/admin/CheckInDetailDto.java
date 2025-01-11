package com.dutact.web.dto.checkin.admin;

import com.dutact.web.data.entity.eventregistration.participationcert.ParticipationCertificateStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CheckInDetailDto {
    @JsonProperty("studentId")
    private Integer studentId;

    @JsonProperty("studentName")
    private String studentName;

    @JsonProperty("studentAvatarUrl")
    private String studentAvatarUrl;

    @JsonProperty("totalCheckIn")
    private Integer totalCheckIn;

    @JsonProperty("certificateStatus")
    private ParticipationCertificateStatus certificateStatus;

    @JsonProperty("checkIns")
    private List<CheckInHistoryDto> checkIns;
} 