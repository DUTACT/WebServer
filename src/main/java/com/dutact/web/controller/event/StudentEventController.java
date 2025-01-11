package com.dutact.web.controller.event;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.common.auth.SecurityContextUtils;
import com.dutact.web.dto.event.student.EventDetailsDto;
import com.dutact.web.dto.event.student.EventDto;
import com.dutact.web.dto.event.student.EventFollowDto;
import com.dutact.web.dto.liker.StudentBasicInfoDto;
import com.dutact.web.service.auth.StudentAccountService;
import com.dutact.web.service.event.student.EventService;
import com.dutact.web.service.event.student.exceptions.FollowForbiddenException;
import com.dutact.web.service.event.student.exceptions.RegisterForbiddenException;
import com.dutact.web.service.event.student.exceptions.UnfollowForbiddenException;
import com.dutact.web.service.event.student.exceptions.UnregisterForbiddenException;
import io.swagger.v3.oas.annotations.Operation;
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
        } catch (RegisterForbiddenException | FollowForbiddenException e) {
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

    @GetMapping("/followers/{eventId}")
    @Operation(summary = "Get list of students who follow an event")
    public ResponseEntity<List<StudentBasicInfoDto>> getEventFollowers(
            @PathVariable Integer eventId) {
        return ResponseEntity.ok(eventService.getEventFollowers(eventId));
    }

    @GetMapping("/registrants/{eventId}")
    @Operation(summary = "Get list of students who registered for an event")
    public ResponseEntity<List<StudentBasicInfoDto>> getEventRegistrants(
            @PathVariable Integer eventId) {
        return ResponseEntity.ok(eventService.getEventRegistrants(eventId));
    }
}
