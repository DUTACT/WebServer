package com.dutact.web.common.notification.infrastructure.api.controllers;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.BadRequestException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.common.auth.SecurityContextUtils;
import com.dutact.web.common.notification.core.dto.NotificationDto;
import com.dutact.web.common.notification.core.dto.NotificationQueryParams;
import com.dutact.web.common.notification.core.service.NotificationService;
import com.dutact.web.service.auth.AccountService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@AllArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<PageResponse<NotificationDto>> getNotifications(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) throws BadRequestException {
        if (page < 1) {
            throw new BadRequestException("Page number must be greater than 0");
        }

        if (size < 1) {
            throw new BadRequestException("Page size must be greater than 0");
        }

        var params = new NotificationQueryParams();
        params.setPage(page);
        params.setSize(size);
        params.setAccountId(accountService.getAccountIdByUsername(SecurityContextUtils.getUsername()));

        return ResponseEntity.ok(notificationService.getNotifications(params));
    }

    @PostMapping("/{notificationId}/mark-as-read")
    public ResponseEntity<NotificationDto> markAsRead(@PathVariable Integer notificationId) throws NotExistsException {
        return ResponseEntity.ok(notificationService.markAsRead(notificationId));
    }

    @PostMapping("/mark-as-read")
    public ResponseEntity<Void> markAsRead(@RequestBody MarkAsReadRequest request) {
        notificationService.markAsRead(request.notificationIds);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/mark-all-as-read")
    public ResponseEntity<Void> markAllAsRead() {
        notificationService.markAllAsRead(accountService.getAccountIdByUsername(SecurityContextUtils.getUsername()));

        return ResponseEntity.ok().build();
    }

    @Data
    public static class MarkAsReadRequest {
        private List<Integer> notificationIds;
    }
}
