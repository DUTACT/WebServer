package com.dutact.web.features.event.admin.controllers;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.OrganizerService;
import com.dutact.web.auth.factors.Role;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.features.event.admin.dtos.EventDto;
import com.dutact.web.features.event.admin.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/admin/events")
public class AdminEventController {

    private final EventService eventService;
    private final OrganizerService organizerService;

    public AdminEventController(EventService eventService, OrganizerService organizerService) {
        this.eventService = eventService;
        this.organizerService = organizerService;
    }

    @GetMapping
    public ResponseEntity<Collection<EventDto>> getEvents() {
        return ResponseEntity.ok(eventService.getEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable Integer id) {
        if (canSeeEvent(id)) {
            return eventService.getEvent(id).map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EventStatus> updateEventStatus(@PathVariable Integer id,
                                                         @RequestBody EventStatus status) {
        return ResponseEntity.ok(eventService.updateEventStatus(id, status));
    }

    private boolean canSeeEvent(Integer eventId) {
        return SecurityContextUtils.hasRole(Role.STUDENT_AFFAIRS_OFFICE)
                || SecurityContextUtils.hasRole(Role.ADMIN)
                || isEventOwner(eventId);
    }

    private boolean isEventOwner(Integer eventId) {
        if (!SecurityContextUtils.hasRole(Role.EVENT_ORGANIZER)) {
            return false;
        }

        String username = SecurityContextUtils.getUsername();
        return organizerService.getOrganizerId(username)
                .map(orgId -> eventService.eventExists(orgId, eventId))
                .orElse(false);
    }
}
