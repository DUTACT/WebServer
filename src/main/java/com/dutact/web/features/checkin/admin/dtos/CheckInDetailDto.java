package com.dutact.web.features.checkin.admin.dtos;

import com.dutact.web.core.entities.eventregistration.participationcert.ParticipationCertificateStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CheckInDetailDto {
    @JsonProperty("studentId")
    private Integer studentId;

    @JsonProperty("studentName")
    private String studentName;

    @JsonProperty("totalCheckIn")
    private Integer totalCheckIn;

    @JsonProperty("certificateStatus")
    private ParticipationCertificateStatus certificateStatus;

    @JsonProperty("checkIns")
    private List<CheckInHistoryDto> checkIns;

    @Data
    public static class CheckInHistoryDto {
        @JsonProperty("id")
        private Integer id;

        @JsonProperty("checkInTime")
        private LocalDateTime checkInTime;

        @JsonProperty("checkInCode")
        private CheckInCodeInfo checkInCode;
    }

    @Data
    public static class CheckInCodeInfo {
        @JsonProperty("id")
        private String id;

        @JsonProperty("title")
        private String title;

        @JsonProperty("startAt")
        private LocalDateTime startAt;

        @JsonProperty("endAt")
        private LocalDateTime endAt;
    }
} 