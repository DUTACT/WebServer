package com.dutact.web.features.analytics.admin.controllers;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.analytics.admin.dtos.registration.EventRegistrationCountByDateDto;
import com.dutact.web.features.analytics.admin.services.EventAnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/analytics/events")
public class OrganizerEventAnalysticController {
    private final EventAnalyticsService eventAnalyticsService;

    public OrganizerEventAnalysticController(EventAnalyticsService eventAnalyticsService) {
        this.eventAnalyticsService = eventAnalyticsService;
    }

    @GetMapping("/{eventId}/registrations")
    public ResponseEntity<List<EventRegistrationCountByDateDto>> getEventRegistrations(
            @PathVariable Integer eventId
    ) throws NotExistsException {
        return ResponseEntity.ok(eventAnalyticsService.getEventRegistrations(eventId));
    }

}
