package com.dutact.web.features.event.admin.controllers;

import com.dutact.web.features.event.admin.services.event.EventService;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/events")
@PreAuthorize("hasRole('STUDENT_AFFAIRS_OFFICE')")
@AllArgsConstructor
public class AdminEventControllerV1 {
    private final EventService eventService;

    @GetMapping
    public String getEvents(
            @RequestParam(name = "organizerId", required = false) @Nullable Integer organizerId,
            @RequestParam(name = "statuses", required = false) @Nullable String statuses,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "orderBy", required = false) @Nullable String orderBy,
            @RequestParam(name = "order", required = false, defaultValue = "asc") String order
    ) {
        return "Hello from AdminEventControllerV1";
    }
}
