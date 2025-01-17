package com.dutact.web.controller.checkin.admin;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.dto.checkin.admin.CreateEventCheckInCodeDto;
import com.dutact.web.dto.checkin.admin.EventCheckInCodeDto;
import com.dutact.web.service.checkin.admin.EventCheckInCodeService;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

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
    public EventCheckInCodeDto getCheckInCode(@PathVariable String id)
            throws NotExistsException {
        return checkInCodeService.getCheckInCode(UUID.fromString(id));
    }

    @PostMapping
    public EventCheckInCodeDto createCheckInCode(
            @RequestBody CreateEventCheckInCodeDto eventParticipationCodeDto)
            throws NotExistsException {
        return checkInCodeService.createCheckInCode(eventParticipationCodeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCheckInCode(@PathVariable String id) {
        checkInCodeService.deleteCheckInCode(UUID.fromString(id));
    }
}
