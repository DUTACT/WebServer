package com.dutact.web.features.checkin.student.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventCheckInResult {
    private LocalDateTime checkInTime;
}
