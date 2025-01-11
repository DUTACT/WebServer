package com.dutact.web.controller.checkin.student;

import com.dutact.web.data.entity.eventregistration.participationcert.ParticipationCertificateStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StudentCheckInDetailDto {
    @JsonProperty("eventId")
    private Integer eventId;

    @JsonProperty("eventTitle")
    private String eventTitle;

    @JsonProperty("totalCheckIn")
    private Integer totalCheckIn;

    @JsonProperty("certificateStatus")
    private ParticipationCertificateStatus certificateStatus;

    @JsonProperty("checkIns")
    private List<CheckInHistoryDto> checkIns;

    @Data
    public static class CheckInHistoryDto {
        @JsonProperty("checkInTime")
        private LocalDateTime checkInTime;

        @JsonProperty("checkInCode")
        private CheckInCodeInfo checkInCode;
    }

    @Data
    public static class CheckInCodeInfo {
        @JsonProperty("title")
        private String title;

        @JsonProperty("startAt")
        private LocalDateTime startAt;

        @JsonProperty("endAt")
        private LocalDateTime endAt;
    }
} 