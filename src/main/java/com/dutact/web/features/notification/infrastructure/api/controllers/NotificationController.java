package com.dutact.web.features.notification.infrastructure.api.controllers;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.AccountService;
import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.BadRequestException;
import com.dutact.web.features.notification.core.dto.NotificationDto;
import com.dutact.web.features.notification.core.dto.NotificationQueryParams;
import com.dutact.web.features.notification.core.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
