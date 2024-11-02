package com.dutact.web.features.participation.admin.controllers;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.participation.admin.dtos.CreateEventParticipationCodeDto;
import com.dutact.web.features.participation.admin.dtos.EventParticipationCodeDto;
import com.dutact.web.features.participation.admin.services.EventParticipationCodeService;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/admin/events/participation-codes")
public class EventParticipationCodeController {
    private final EventParticipationCodeService participationCodeService;

    public EventParticipationCodeController(
            EventParticipationCodeService participationCodeService) {
        this.participationCodeService = participationCodeService;
    }

    @GetMapping
    public Collection<EventParticipationCodeDto> getEventParticipationCodes(@NotNull @RequestParam Integer eventId)
            throws NotExistsException {
        return participationCodeService.getEventParticipationCodes(eventId);
    }

    @GetMapping("/{id}")
    public EventParticipationCodeDto getEventParticipationCode(@PathVariable Integer id)
            throws NotExistsException {
        return participationCodeService.getEventParticipationCode(id);
    }

    @PostMapping
    public EventParticipationCodeDto createEventParticipationCode(
            @NotNull @RequestParam Integer eventId,
            @RequestBody CreateEventParticipationCodeDto eventParticipationCodeDto)
            throws NotExistsException {
        return participationCodeService.createEventParticipationCode(eventId, eventParticipationCodeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteEventParticipationCode(@PathVariable Integer id) {
        participationCodeService.deleteEventParticipationCode(id);
    }
}
