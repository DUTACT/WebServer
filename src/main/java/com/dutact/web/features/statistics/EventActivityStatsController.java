package com.dutact.web.features.statistics;

import com.dutact.web.features.statistics.dto.MonthlyActivityStatsDto;
import com.dutact.web.features.statistics.services.EventActivityStatsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventActivityStatsController {
    private final EventActivityStatsService activityStatsService;

    @GetMapping("/api/events/{eventId}/activity-stats")
    @Operation(summary = "Get daily activity statistics for the past month")
    public ResponseEntity<MonthlyActivityStatsDto> getEventActivityStats(
            @PathVariable Integer eventId) {
        return ResponseEntity.ok(activityStatsService.getMonthlyActivityStats(eventId));
    }
} 