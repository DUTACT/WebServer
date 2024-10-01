package com.dutact.web.features.event.admin.controllers;

import com.dutact.web.features.event.admin.dtos.EventCreateUpdateDto;
import com.dutact.web.features.event.admin.dtos.EventDto;
import com.dutact.web.features.event.admin.services.EventService;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController("organizerEventController")
@RequestMapping("/api/organizers/{orgId}/events")
public class OrganizerEventController {
    private final EventService eventService;

    public OrganizerEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @SneakyThrows
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventDto> createEvent(@PathVariable Integer orgId, @RequestBody EventCreateUpdateDto eventDto) {
        EventDto createdEvent = eventService.createEvent(orgId, eventDto);
        return ResponseEntity.created(new URI("/api/organizers/" + orgId + "/events/" + createdEvent.getId()))
                .body(eventService.createEvent(orgId, eventDto));
    }

    @GetMapping
    public ResponseEntity<Collection<EventDto>> getEvents(@PathVariable Integer orgId) {
        return ResponseEntity.ok(eventService.getEvents(orgId));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> getEvent(@PathVariable Integer orgId, @PathVariable Integer eventId) {
        if (!eventService.eventExists(orgId, eventId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(eventService.getEvent(eventId));
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventDto> updateEvent(
            @PathVariable Integer orgId,
            @PathVariable Integer eventId,
            @RequestBody EventCreateUpdateDto eventDto) {
        if (!eventService.eventExists(orgId, eventId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(eventService.updateEvent(eventId, eventDto));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deleteEvent(
            @PathVariable Integer orgId,
            @PathVariable Integer eventId) {
        if (!eventService.eventExists(orgId, eventId)) {
            return ResponseEntity.notFound().build();
        }

        eventService.deleteEvent(eventId);
        return ResponseEntity.ok().build();
    }
}
