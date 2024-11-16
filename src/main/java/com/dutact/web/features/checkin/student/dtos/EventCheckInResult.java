package com.dutact.web.features.checkin.student.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventCheckInResult {
    private Integer eventId;
    private String eventName;
    private LocalDateTime checkInTime;
}
