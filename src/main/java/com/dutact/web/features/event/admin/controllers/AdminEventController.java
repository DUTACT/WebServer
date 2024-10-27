package com.dutact.web.features.event.admin.controllers;

import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.features.event.admin.dtos.event.EventDto;
import com.dutact.web.features.event.admin.dtos.event.RejectEventDto;
import com.dutact.web.features.event.admin.services.event.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/admin/events")
@PreAuthorize("hasRole('STUDENT_AFFAIRS_OFFICE')")
public class AdminEventController {

    private final EventService eventService;

    public AdminEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<Collection<EventDto>> getEvents() {
        return ResponseEntity.ok(eventService.getEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEvent(@PathVariable Integer id) {
        return eventService.getEvent(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<EventStatus> approveEvent(@PathVariable Integer id)
            throws NotExistsException, ConflictException {
        return ResponseEntity.ok(eventService.approveEvent(id));
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<EventStatus> rejectEvent(
            @PathVariable Integer id,
            @RequestBody RejectEventDto rejectEventDto)
            throws NotExistsException, ConflictException {
        return ResponseEntity.ok(eventService.rejectEvent(id, rejectEventDto.getReason()));
    }
}
