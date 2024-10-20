package com.dutact.web.features.event.admin.controllers;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.OrganizerAccountService;
import com.dutact.web.auth.factors.Role;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.features.event.admin.dtos.event.EventCreateDto;
import com.dutact.web.features.event.admin.dtos.event.EventDto;
import com.dutact.web.features.event.admin.dtos.event.EventUpdateDto;
import com.dutact.web.features.event.admin.dtos.event.RejectEventDto;
import com.dutact.web.features.event.admin.services.event.EventService;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/admin/events")
public class AdminEventController {

    private final EventService eventService;
    private final OrganizerAccountService organizerAccountService;

    public AdminEventController(EventService eventService, OrganizerAccountService organizerAccountService) {
        this.eventService = eventService;
        this.organizerAccountService = organizerAccountService;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @SneakyThrows
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventDto> createEvent(@ModelAttribute EventCreateDto eventDto) {
        if (!canManageOwnEvents()) {
            return ResponseEntity.status(403).build();
        }

        int requestOrgId = organizerAccountService.getOrganizerId(SecurityContextUtils.getUsername())
                .orElseThrow();
        if (eventDto.getOrganizerId() != requestOrgId) {
            return ResponseEntity.status(403).build();
        }

        EventDto createdEvent = eventService.createEvent(eventDto);
        return ResponseEntity.created(new URI("/api/events/" + createdEvent.getId()))
                .body(createdEvent);
    }

    @GetMapping
    public ResponseEntity<Collection<EventDto>> getEvents() {
        if (SecurityContextUtils.hasRole(Role.EVENT_ORGANIZER)) {
            String username = SecurityContextUtils.getUsername();
            return ResponseEntity.ok(eventService
                    .getEvents(organizerAccountService.getOrganizerId(username).orElseThrow()));
        }

        return ResponseEntity.ok(eventService.getEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEvent(@PathVariable Integer id) {
        if (SecurityContextUtils.hasRole(Role.EVENT_ORGANIZER) && !isEventOwner(id)) {
            return ResponseEntity.status(403).build();
        }

        return eventService.getEvent(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping(path = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<EventDto> updateEvent(@PathVariable Integer id,
                                                @ModelAttribute EventUpdateDto eventDto) {
        if (!(canManageOwnEvents() && isEventOwner(id))) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(eventService.updateEvent(id, eventDto));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<EventStatus> approveEvent(@PathVariable Integer id) {
        if (!SecurityContextUtils.hasRole(Role.STUDENT_AFFAIRS_OFFICE)) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(eventService.approveEvent(id));
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<EventStatus> rejectEvent(
            @PathVariable Integer id,
            @RequestBody RejectEventDto rejectEventDto) {
        if (!SecurityContextUtils.hasRole(Role.STUDENT_AFFAIRS_OFFICE)) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(eventService.rejectEvent(id, rejectEventDto.getReason()));
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
        return organizerAccountService.getOrganizerId(username)
                .map(orgId -> eventService.eventExists(orgId, eventId))
                .orElse(false);
    }
}
