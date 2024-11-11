package com.dutact.web.features.checkin.admin.controllers;

import com.dutact.web.features.checkin.admin.dtos.participation.ConfirmParticipationCriterion;
import com.dutact.web.features.checkin.admin.dtos.participation.RejectParticipationCriterion;
import com.dutact.web.features.checkin.admin.services.EventParticipationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/events/{eventId}/participation")
public class AdminEventParticipationController {
    private final EventParticipationService eventParticipationService;

    public AdminEventParticipationController(EventParticipationService eventParticipationService) {
        this.eventParticipationService = eventParticipationService;
    }

    @PostMapping("/confirm")
    public void confirm(
            @PathVariable Integer eventId,
            @RequestBody ConfirmParticipationCriterion confirmCriterion) {
        eventParticipationService.confirmParticipation(eventId, confirmCriterion);
    }

    @PostMapping("/reject")
    public void reject(
            @PathVariable Integer eventId,
            @RequestBody RejectParticipationCriterion rejectCriterion) {
        eventParticipationService.rejectParticipation(eventId, rejectCriterion);
    }
}
