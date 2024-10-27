package com.dutact.web.features.analytics.admin.controllers;

import com.dutact.web.common.api.exceptions.ForbiddenException;
import com.dutact.web.features.analytics.admin.dtos.registration.EventRegistrationsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/analytics/events")
public class OrganizerEventAnalysticController {
    @GetMapping("/{eventId}/registrations")
    public ResponseEntity<List<EventRegistrationsDto>> getEventRegistrations(
            @RequestParam("organizerId") Integer organizerId,
            @RequestParam("eventId") Integer eventId) throws ForbiddenException {
        validateRequestOrganizer(organizerId);

        return ResponseEntity.ok(eventService.getEventRegistrations(eventId));
    }

}
