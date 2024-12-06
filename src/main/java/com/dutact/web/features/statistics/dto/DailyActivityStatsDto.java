package com.dutact.web.features.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyActivityStatsDto {
    private LocalDate date;
    private Long followers;
    private Long postLikes;
    private Long feedbackLikes;
    private Long registrations;
} 