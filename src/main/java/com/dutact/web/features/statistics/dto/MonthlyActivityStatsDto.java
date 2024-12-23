package com.dutact.web.features.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyActivityStatsDto {
    private List<DailyActivityStatsDto> dailyStats;
} 