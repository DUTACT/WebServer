package com.dutact.web.features.event.student.controllers;

import com.dutact.web.features.event.student.dtos.EventDto;
import com.dutact.web.features.event.student.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class StudentEventController {
    private final EventService eventService;

    public StudentEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEvent(@PathVariable Integer id) {
        Optional<EventDto> event = eventService.getEvent(id);
        return event.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping
    public List<EventDto> getEvents() {
        return eventService.getEvents();
    }
}
