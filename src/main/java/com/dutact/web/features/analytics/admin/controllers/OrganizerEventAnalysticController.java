package com.dutact.web.features.analytics.admin.controllers;

import com.dutact.web.common.api.exceptions.ForbiddenException;
import com.dutact.web.features.analytics.admin.dtos.registration.EventRegistrationCountByDateDto;
import com.dutact.web.features.analytics.admin.dtos.registration.EventRegistrationQueryParams;
import jakarta.websocket.server.PathParam;
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
    public ResponseEntity<List<EventRegistrationCountByDateDto>> getEventRegistrations(
            @PathParam("eventId") Integer eventId,
            @RequestParam EventRegistrationQueryParams queryParams
    ) throws ForbiddenException {
        return ResponseEntity.ok(eventService.getEventRegistrations(eventId));
    }

}
