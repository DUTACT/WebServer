package com.dutact.web.features.event.student.controllers;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.StudentAccountService;
import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.event.student.dtos.EventDto;
import com.dutact.web.features.event.student.dtos.EventFollowDto;
import com.dutact.web.features.event.student.dtos.EventDetailsDto;
import com.dutact.web.features.event.student.services.EventService;
import com.dutact.web.features.event.student.services.exceptions.FollowForbiddenException;
import com.dutact.web.features.event.student.services.exceptions.RegisterForbiddenException;
import com.dutact.web.features.event.student.services.exceptions.UnfollowForbiddenException;
import com.dutact.web.features.event.student.services.exceptions.UnregisterForbiddenException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class StudentEventController {
    private final EventService eventService;
    private final StudentAccountService studentAccountService;

    public StudentEventController(EventService eventService,
                                  StudentAccountService studentAccountService) {
        this.eventService = eventService;
        this.studentAccountService = studentAccountService;
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

    @PostMapping("/{id}/register")
    @ApiResponse(responseCode = "200", description = "Student successfully registered")
    public ResponseEntity<?> register(@PathVariable Integer id)
            throws NotExistsException, ConflictException {
        Integer requestStudentId = studentAccountService
                .getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("The account is not associated with any student profile"));

        try {
            return ResponseEntity.ok(eventService.register(id, requestStudentId));
        } catch (RegisterForbiddenException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    @PostMapping("/{id}/unregister")
    @ApiResponse(responseCode = "204", description = "Student successfully unregistered")
    public ResponseEntity<?> unregister(@PathVariable Integer id)
            throws NotExistsException, ConflictException {
        Integer requestStudentId = studentAccountService
                .getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("The account is not associated with any student profile"));

        try {
            eventService.unregister(id, requestStudentId);
            return ResponseEntity.noContent().build();
        } catch (UnregisterForbiddenException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    @PostMapping("/{id}/follow")
    public ResponseEntity<EventFollowDto> follow(@PathVariable Integer id)
            throws NotExistsException, ConflictException {
        Integer requestStudentId = studentAccountService
                .getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("The account is not associated with any student profile"));

        try {
            return ResponseEntity.ok(eventService.follow(id, requestStudentId));
        } catch (FollowForbiddenException e) {
            throw new ConflictException("Student already follow for this event");
        }
    }

    @PostMapping("/{id}/unfollow")
    @ApiResponse(responseCode = "204", description = "Student successfully unfollowed")
    public ResponseEntity<?> unfollow(@PathVariable Integer id)
            throws NotExistsException, ConflictException {
        Integer requestStudentId = studentAccountService
                .getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("The account is not associated with any student profile"));

        try {
            eventService.unfollow(id, requestStudentId);
            return ResponseEntity.noContent().build();
        } catch (UnfollowForbiddenException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    @GetMapping("/registered")
    public PageResponse<EventDetailsDto> getRegisteredEvents(
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer pageSize) {
        Integer requestStudentId = studentAccountService
                .getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("The account is not associated with any student profile"));
        return eventService.getRegisteredEvents(requestStudentId, page, pageSize);
    }

    @GetMapping("/followed")
    public PageResponse<EventDetailsDto> getFollowedEvents(
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer pageSize) {
        Integer requestStudentId = studentAccountService
                .getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("The account is not associated with any student profile"));
        return eventService.getFollowedEvents(requestStudentId, page, pageSize);
    }

    @GetMapping("/confirmed")
    public PageResponse<EventDetailsDto> getConfirmedEvents(
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer pageSize) {
        Integer requestStudentId = studentAccountService
                .getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("The account is not associated with any student profile"));
        return eventService.getConfirmedEvents(requestStudentId, page, pageSize);
    }
}
