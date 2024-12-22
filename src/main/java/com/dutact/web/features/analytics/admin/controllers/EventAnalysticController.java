package com.dutact.web.features.analytics.admin.controllers;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.analytics.admin.dtos.registration.EventRegistrationCountByDateDto;
import com.dutact.web.features.analytics.admin.dtos.organizer.OrganizerOverallStatsDto;
import com.dutact.web.features.analytics.admin.services.EventAnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

import java.util.List;

@RestController
@RequestMapping("/api/admin/analytics")
public class EventAnalysticController {
    private final EventAnalyticsService eventAnalyticsService;

    public EventAnalysticController(EventAnalyticsService eventAnalyticsService) {
        this.eventAnalyticsService = eventAnalyticsService;
    }

    @GetMapping("/{eventId}/registrations")
    public ResponseEntity<List<EventRegistrationCountByDateDto>> getEventRegistrations(
            @PathVariable Integer eventId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) throws NotExistsException {
        return ResponseEntity.ok(eventAnalyticsService.getEventRegistrations(eventId, startDate, endDate));
    }

    @GetMapping("/{eventId}/participations")
    @Operation(summary = "Get approved event participation counts by date")
    public ResponseEntity<List<EventRegistrationCountByDateDto>> getEventParticipations(
            @PathVariable Integer eventId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) throws NotExistsException {
        return ResponseEntity.ok(eventAnalyticsService.getEventParticipations(eventId, startDate, endDate));
    }

    @GetMapping("/organizer/overall-stats")
    @Operation(summary = "Get overall statistics for current logged-in organizer")
    public ResponseEntity<OrganizerOverallStatsDto> getCurrentOrganizerOverallStats() 
            throws NotExistsException {
        String username = SecurityContextUtils.getUsername();
        return ResponseEntity.ok(eventAnalyticsService.getOrganizerOverallStatsByUsername(username));
    }
}
