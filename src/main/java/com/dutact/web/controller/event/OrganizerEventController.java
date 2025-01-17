package com.dutact.web.controller.event;

import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.ForbiddenException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.common.auth.SecurityContextUtils;
import com.dutact.web.dto.event.admin.*;
import com.dutact.web.data.entity.auth.Role;
import com.dutact.web.service.auth.OrganizerAccountService;
import com.dutact.web.service.event.admin.EventService;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/organizers/{id}/events")
public class OrganizerEventController {
    private final EventService eventService;
    private final OrganizerAccountService organizerAccountService;

    public OrganizerEventController(EventService eventService,
                                    OrganizerAccountService organizerAccountService) {
        this.eventService = eventService;
        this.organizerAccountService = organizerAccountService;
    }

    @SneakyThrows(value = {URISyntaxException.class})
    @PostMapping(path = "/v2", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<EventDto> createEventV2(
            @PathVariable("id") Integer organizerId,
            @ModelAttribute EventCreateDtoV2 eventDto)
            throws ForbiddenException, NotExistsException, ConflictException {
        if (!canManageOwnEvents()) {
            return ResponseEntity.status(403).build();
        }

        validateRequestOrganizer(organizerId);

        EventDto createdEvent = eventService.createEvent(organizerId, eventDto);
        return ResponseEntity.created(new URI("/api/events/" + createdEvent.getId()))
                .body(createdEvent);
    }

    @SneakyThrows(value = {URISyntaxException.class})
    @PostMapping(path = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<EventDto> createEventV1(
            @PathVariable("id") Integer organizerId,
            @ModelAttribute EventCreateDtoV1 eventDto)
            throws ForbiddenException, NotExistsException, ConflictException {
        if (!canManageOwnEvents()) {
            return ResponseEntity.status(403).build();
        }

        validateRequestOrganizer(organizerId);

        EventDto createdEvent = eventService.createEvent(organizerId, eventDto);
        return ResponseEntity.created(new URI("/api/events/" + createdEvent.getId()))
                .body(createdEvent);

    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getEvents(
            @PathVariable("id") Integer organizerId) throws ForbiddenException, NotExistsException {
        if (!SecurityContextUtils.hasRole(Role.STUDENT_AFFAIRS_OFFICE)) {
            validateRequestOrganizer(organizerId);
        }

        return ResponseEntity.ok(eventService
                .getEvents(organizerId));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> getEvent(
            @PathVariable("id") Integer organizerId,
            @PathVariable("eventId") Integer eventId) throws ForbiddenException {
        if (!SecurityContextUtils.hasRole(Role.STUDENT_AFFAIRS_OFFICE)) {
            validateRequestOrganizer(organizerId);
        }

        Optional<EventDto> eventOpt = eventService.getEvent(eventId);
        if (eventOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        EventDto event = eventOpt.get();
        if (!Objects.equals(event.getOrganizer().getId(), organizerId)) {
            throw new ForbiddenException("You are not allowed to access this organizer's events.");
        }

        return ResponseEntity.ok(event);
    }

    @GetMapping("/{eventId}/change-history")
    public ResponseEntity<List<EventChangeDto>> getEventChangeHistory(
            @PathVariable("id") Integer organizerId,
            @PathVariable("eventId") Integer eventId) throws ForbiddenException {
        validateRequestOrganizer(organizerId);

        return ResponseEntity.ok(eventService.getEventChangeHistory(eventId));
    }


    @PatchMapping(path = "/{eventId}/v2", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<EventDto> updateEventV2(
            @PathVariable("id") Integer organizerId,
            @PathVariable("eventId") Integer eventId,
            @ModelAttribute EventUpdateDtoV2 eventDto) throws ForbiddenException, NotExistsException {
        validateRequestOrganizer(organizerId);

        return ResponseEntity.ok(eventService.updateEvent(eventId, eventDto));
    }

    @PatchMapping(path = "/{eventId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<EventDto> updateEventV1(
            @PathVariable("id") Integer organizerId,
            @PathVariable("eventId") Integer eventId,
            @ModelAttribute EventUpdateDtoV1 eventDto) throws ForbiddenException, NotExistsException {
        validateRequestOrganizer(organizerId);

        return ResponseEntity.ok(eventService.updateEvent(eventId, eventDto));
    }

    @PostMapping("/{eventId}/renew-registration")
    public ResponseEntity<EventDto> renewEventRegistration(
            @PathVariable("id") Integer organizerId,
            @PathVariable("eventId") Integer eventId,
            @RequestBody RenewEventRegistrationDto renewEventRegistrationDto)
            throws ForbiddenException, NotExistsException {
        validateRequestOrganizer(organizerId);

        return ResponseEntity.ok(eventService.renewEventRegistration(eventId, renewEventRegistrationDto));
    }

    @PostMapping("/{eventId}/close-registration")
    public ResponseEntity<EventDto> closeEventRegistration(
            @PathVariable("id") Integer organizerId,
            @PathVariable("eventId") Integer eventId)
            throws ForbiddenException, NotExistsException, ConflictException {
        validateRequestOrganizer(organizerId);

        return ResponseEntity.ok(eventService.closeEventRegistration(eventId));
    }

    @PostMapping("/{eventId}/change-time")
    public ResponseEntity<EventDto> changeEventTime(
            @PathVariable("id") Integer organizerId,
            @PathVariable("eventId") Integer eventId,
            @RequestBody ChangeEventTimeDto changeEventTimeDto)
            throws ForbiddenException, NotExistsException {
        validateRequestOrganizer(organizerId);

        return ResponseEntity.ok(eventService.changeEventTime(eventId, changeEventTimeDto));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable("id") Integer organizerId,
            @PathVariable("eventId") Integer eventId) throws ForbiddenException {
        validateRequestOrganizer(organizerId);

        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }

    private void validateRequestOrganizer(Integer requestParamOrgId) throws ForbiddenException {
        if (!canManageOwnEvents()) {
            throw new ForbiddenException("You are not allowed to access this organizer's events.");
        }

        var requestOrgId = organizerAccountService.getOrganizerId(SecurityContextUtils.getUsername())
                .orElseThrow(() -> new IllegalStateException("Organizer not found"));
        if (!Objects.equals(requestParamOrgId, requestOrgId)) {
            throw new ForbiddenException("You are not allowed to access this organizer's events.");
        }
    }

    private boolean canManageOwnEvents() {
        return SecurityContextUtils.hasRole(Role.EVENT_ORGANIZER)
                || SecurityContextUtils.hasRole(Role.STUDENT_AFFAIRS_OFFICE);
    }
}
