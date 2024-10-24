package com.dutact.web.features.event.student.controllers;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.StudentAccountService;
import com.dutact.web.common.api.ErrorMessage;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.event.student.dtos.EventDto;
import com.dutact.web.features.event.student.dtos.EventRegisteredDto;
import com.dutact.web.features.event.student.services.EventService;
import com.dutact.web.features.event.student.services.exceptions.AlreadyFollowedException;
import com.dutact.web.features.event.student.services.exceptions.AlreadyRegisteredException;
import com.dutact.web.features.event.student.services.exceptions.NotFollowedException;
import com.dutact.web.features.event.student.services.exceptions.NotRegisteredException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student successfully registered",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventRegisteredDto.class))),
            @ApiResponse(responseCode = "409", description = "Already registered")
    })
    public ResponseEntity<?> register(@PathVariable Integer id)
            throws NotExistsException {
        Integer requestStudentId = studentAccountService
                .getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("The account is not associated with any student profile"));

        try {
            return ResponseEntity.ok(eventService.register(id, requestStudentId));
        } catch (AlreadyRegisteredException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Student already registered for this event"));
        }
    }

    @PostMapping("/{id}/unregister")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student successfully unregistered"),
            @ApiResponse(responseCode = "409", description = "Student not registered")
    })
    public ResponseEntity<?> unregister(@PathVariable Integer id)
            throws NotExistsException {
        Integer requestStudentId = studentAccountService
                .getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("The account is not associated with any student profile"));

        try {
            eventService.unregister(id, requestStudentId);
            return ResponseEntity.noContent().build();
        } catch (NotRegisteredException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Student not registered for this event"));
        }
    }

    @PostMapping("/{id}/follow")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student successfully followed the event",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventRegisteredDto.class))),
            @ApiResponse(responseCode = "409", description = "Already followed")
    })
    public ResponseEntity<?> follow(@PathVariable Integer id)
            throws NotExistsException {
        Integer requestStudentId = studentAccountService
                .getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("The account is not associated with any student profile"));

        try {
            return ResponseEntity.ok(eventService.follow(id, requestStudentId));
        } catch (AlreadyFollowedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorMessage("Student already follow this event"));
        }
    }

    @PostMapping("/{id}/unfollow")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student successfully follow"),
            @ApiResponse(responseCode = "409", description = "Student not follow")
    })
    public ResponseEntity<?> unfollow(@PathVariable Integer id)
            throws NotExistsException {
        Integer requestStudentId = studentAccountService
                .getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("The account is not associated with any student profile"));

        try {
            eventService.unfollow(id, requestStudentId);
            return ResponseEntity.noContent().build();
        } catch (NotFollowedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorMessage("Student not follow for this event"));
        }
    }
}
