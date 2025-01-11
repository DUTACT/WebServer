package com.dutact.web.dto.analytics.registration;

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