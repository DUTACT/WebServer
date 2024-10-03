package com.dutact.web.features.event.admin.controllers;

import com.dutact.web.auth.factors.RoleName;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.features.event.admin.dtos.EventDto;
import com.dutact.web.features.event.admin.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/events")
@PreAuthorize("hasRole(" + RoleName.STUDENT_AFFAIRS_OFFICE + ")")
public class AdminEventController {

    public EventService eventService;

    public AdminEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<Collection<EventDto>> getEvents() {
        return ResponseEntity.ok(eventService.getEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable Integer id) {
        Optional<EventDto> event = eventService.getEvent(id);
        if (event.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(event);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EventStatus> updateEventStatus(@PathVariable Integer id, @RequestBody EventStatus status) {
        return ResponseEntity.ok(eventService.updateEventStatus(id, status));
    }
}
