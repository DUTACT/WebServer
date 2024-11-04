package com.dutact.web.features.checkin.admin.controllers;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.checkin.admin.dtos.CreateEventCheckInCodeDto;
import com.dutact.web.features.checkin.admin.dtos.EventCheckInCodeDto;
import com.dutact.web.features.checkin.admin.services.EventCheckInCodeService;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/admin/events/checkin-codes")
public class EventCheckInCodeController {
    private final EventCheckInCodeService checkInCodeService;

    public EventCheckInCodeController(
            EventCheckInCodeService checkInCodeService) {
        this.checkInCodeService = checkInCodeService;
    }

    @GetMapping
    public Collection<EventCheckInCodeDto> getCheckInCodes(@NotNull @RequestParam Integer eventId)
            throws NotExistsException {
        return checkInCodeService.getCheckInCodes(eventId);
    }

    @GetMapping("/{id}")
    public EventCheckInCodeDto getCheckInCode(@PathVariable Integer id)
            throws NotExistsException {
        return checkInCodeService.getCheckInCode(id);
    }

    @PostMapping
    public EventCheckInCodeDto createCheckInCode(
            @RequestBody CreateEventCheckInCodeDto eventParticipationCodeDto)
            throws NotExistsException {
        return checkInCodeService.createCheckInCode(eventParticipationCodeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCheckInCode(@PathVariable Integer id) {
        checkInCodeService.deleteCheckInCode(id);
    }
}
