package com.dutact.web.features.event.admin.controllers;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.OrganizerService;
import com.dutact.web.auth.factors.Role;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.features.event.admin.dtos.EventCreateDto;
import com.dutact.web.features.event.admin.dtos.EventDto;
import com.dutact.web.features.event.admin.dtos.EventUpdateDto;
import com.dutact.web.features.event.admin.services.EventService;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @PostMapping
    @SneakyThrows
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventDto> createEvent(@RequestBody EventCreateDto eventDto) {
        if (!canManageOwnEvents()) {
            return ResponseEntity.status(403).build();
        }

        int requestOrgId = organizerService.getOrganizerId(SecurityContextUtils.getUsername())
                .orElseThrow();
        if (eventDto.getOrganizerId() != requestOrgId) {
            return ResponseEntity.status(403).build();
        }

        EventDto createdEvent = eventService.createEvent(eventDto);
        return ResponseEntity.created(new URI("/api/events/" + createdEvent.getId()))
                .body(eventService.createEvent(eventDto));
    }

    @GetMapping
    public ResponseEntity<Collection<EventDto>> getEvents() {
        if (SecurityContextUtils.hasRole(Role.EVENT_ORGANIZER)) {
            String username = SecurityContextUtils.getUsername();
            return ResponseEntity.ok(eventService
                    .getEvents(organizerService.getOrganizerId(username).orElseThrow()));
        }

        return ResponseEntity.ok(eventService.getEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable Integer id) {
        if (SecurityContextUtils.hasRole(Role.EVENT_ORGANIZER) && !isEventOwner(id)) {
            return ResponseEntity.status(403).build();
        }

        return eventService.getEvent(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Integer id,
                                         @RequestBody EventUpdateDto eventDto) {
        if (!(canManageOwnEvents() && isEventOwner(id))) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(eventService.updateEvent(id, eventDto));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EventStatus> updateEventStatus(@PathVariable Integer id,
                                                         @RequestBody EventStatus status) {
        if (!SecurityContextUtils.hasRole(Role.STUDENT_AFFAIRS_OFFICE)) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(eventService.updateEventStatus(id, status));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Integer eventId) {
        if (!(canManageOwnEvents() && isEventOwner(eventId))) {
            return ResponseEntity.status(403).build();
        }

        eventService.deleteEvent(eventId);
        return ResponseEntity.ok().build();
    }

    private boolean canManageOwnEvents() {
        return SecurityContextUtils.hasRole(Role.EVENT_ORGANIZER)
                || SecurityContextUtils.hasRole(Role.STUDENT_AFFAIRS_OFFICE);
    }

    private boolean isEventOwner(Integer eventId) {
        String username = SecurityContextUtils.getUsername();
        return organizerService.getOrganizerId(username)
                .map(orgId -> eventService.eventExists(orgId, eventId))
                .orElse(false);
    }
}
