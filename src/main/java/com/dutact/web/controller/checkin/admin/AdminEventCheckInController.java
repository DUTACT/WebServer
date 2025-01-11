package com.dutact.web.controller.checkin.admin;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.data.repository.views.CheckInQueryParams;
import com.dutact.web.dto.checkin.admin.CheckInDetailDto;
import com.dutact.web.dto.checkin.admin.CheckInPreviewDto;
import com.dutact.web.service.checkin.admin.EventCheckInService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
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
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer pageSize) {

        var queryParam = new CheckInQueryParams(eventId);
        queryParam.setSearchQuery(searchQuery);
        queryParam.setPage(page);
        queryParam.setPageSize(pageSize);

        return eventCheckInService.getCheckedInParticipants(queryParam);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<CheckInDetailDto> getCheckInDetail(
            @PathVariable Integer eventId,
            @PathVariable Integer studentId) throws NotExistsException {
        return ResponseEntity.ok(eventCheckInService.getCheckInDetail(eventId, studentId));
    }
}
