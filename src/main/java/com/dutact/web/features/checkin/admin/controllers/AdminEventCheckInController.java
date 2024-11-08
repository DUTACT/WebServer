package com.dutact.web.features.checkin.admin.controllers;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.core.repositories.views.CheckInQueryParams;
import com.dutact.web.features.checkin.admin.dtos.CheckInPreviewDto;
import com.dutact.web.features.checkin.admin.services.EventCheckInService;
import jakarta.annotation.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/events/{eventId}/check-in")
public class AdminEventCheckInController {
    private final EventCheckInService eventCheckInService;

    public AdminEventCheckInController(EventCheckInService eventCheckInService) {
        this.eventCheckInService = eventCheckInService;
    }

    @GetMapping
    public PageResponse<CheckInPreviewDto> getCheckedInParticipants(
            @PathVariable Integer eventId,
            @RequestParam(required = false) @Nullable String searchQuery,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {

        var queryParam = new CheckInQueryParams(eventId);
        queryParam.setSearchQuery(searchQuery);
        queryParam.setPage(page);
        queryParam.setPageSize(pageSize);

        return eventCheckInService.getCheckedInParticipants(queryParam);
    }

}
