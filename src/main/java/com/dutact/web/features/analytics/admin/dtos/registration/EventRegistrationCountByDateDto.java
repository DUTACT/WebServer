package com.dutact.web.features.analytics.admin.dtos.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRegistrationCountByDateDto {
    private LocalDate date;
    private Integer count;
}