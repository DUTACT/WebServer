package com.dutact.web.features.analytics.admin.dtos.registration;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventRegistrationCountByDateDto {
    private LocalDate date;
    private Integer count;
}