package com.dutact.web.features.checkin.admin.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/event-participation")
public class AdminEventParticipationController {
    @PostMapping("/confirm")
    public void confirm() {
    }

    @PostMapping("/reject")
    public void reject() {
    }
}
