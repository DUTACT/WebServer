package com.dutact.web.features.analytics.admin.controllers;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.AccountRepository;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.analytics.admin.dtos.RegistrationAndParticipationCountDto;
import com.dutact.web.features.analytics.admin.dtos.organizer.OrganizerOverallStatsDto;
import com.dutact.web.features.analytics.admin.dtos.registration.EventRegistrationCountByDateDto;
import com.dutact.web.features.analytics.admin.services.EventAnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/analytics")
public class EventAnalysticController {
    private final EventAnalyticsService eventAnalyticsService;
    private final AccountRepository accountRepository;

    @GetMapping("/events/{eventId}/registrations")
    public ResponseEntity<List<EventRegistrationCountByDateDto>> getEventRegistrations(
            @PathVariable Integer eventId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) throws NotExistsException {
        if (startDate == null || endDate == null) {
            return ResponseEntity.ok(eventAnalyticsService.getEventRegistrations(eventId));
        }
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

    @GetMapping("/organizer/registrations")
    public ResponseEntity<List<EventRegistrationCountByDateDto>> getRegistrationStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        String username = SecurityContextUtils.getUsername();
        Integer organizerId = accountRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException("Organizer not found")).getId();
        return ResponseEntity.ok(
                eventAnalyticsService.getRegistrationStats(organizerId, startDate, endDate)
        );
    }

    @GetMapping("/organizer/participations")
    @Operation(summary = "Get participation statistics for all events")
    public ResponseEntity<List<EventRegistrationCountByDateDto>> getParticipationStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        String username = SecurityContextUtils.getUsername();
        Integer organizerId = accountRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException("Organizer not found")).getId();
        return ResponseEntity.ok(
                eventAnalyticsService.getParticipationStats(organizerId, startDate, endDate)
        );
    }

    @GetMapping("/organizer/registrations-and-participations")
    @Operation(summary = "Get participation statistics for all events")
    public ResponseEntity<RegistrationAndParticipationCountDto> getRegistrationAndParticipationStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        String username = SecurityContextUtils.getUsername();
        Integer organizerId = accountRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException("Organizer not found")).getId();
        return ResponseEntity.ok(
                new RegistrationAndParticipationCountDto(
                        eventAnalyticsService.getRegistrationStats(organizerId, startDate, endDate),
                        eventAnalyticsService.getParticipationStats(organizerId, startDate, endDate)
                )
        );
    }
}
