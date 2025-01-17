package com.dutact.web.common.notification.infrastructure.api.controllers;

import com.dutact.web.common.api.exceptions.ForbiddenException;
import com.dutact.web.common.auth.SecurityContextUtils;
import com.dutact.web.common.mapper.ObjectMapperUtils;
import com.dutact.web.common.notification.infrastructure.api.dtos.SubscribeDto;
import com.dutact.web.common.notification.infrastructure.api.dtos.SubscribeResponseDto;
import com.dutact.web.common.notification.infrastructure.api.dtos.UnsubscribeDto;
import com.dutact.web.common.notification.infrastructure.push.PushNotificationQueue;
import com.dutact.web.common.notification.subscription.AccountSubscriptionHandler;
import com.dutact.web.service.auth.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/push-notification")
@AllArgsConstructor
public class PushNotificationController {
    private final ObjectMapper objectMapper = ObjectMapperUtils.createObjectMapper();
    private final AccountSubscriptionHandler accountSubscriptionHandler;
    private final AccountService accountService;
    private final PushNotificationQueue pushNotificationQueue;

    @PostMapping("subscribe")
    public ResponseEntity<SubscribeResponseDto> subscribe(SubscribeDto subscribeDto) throws ForbiddenException {
        if (!SecurityContextUtils.hasAuthentication()) {
            throw new ForbiddenException();
        }

        var accountId = accountService.getAccountIdByUsername(SecurityContextUtils.getUsername());
        var subscriptionToken = accountSubscriptionHandler.subscribe(
                subscribeDto.getDeviceId(), accountId);

        var responseDto = new SubscribeResponseDto();
        responseDto.setSubscriptionToken(subscriptionToken);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("unsubscribe")
    public void unsubscribe(UnsubscribeDto unsubscribeDto) {
        accountSubscriptionHandler.unsubscribe(unsubscribeDto.getSubscriptionToken());
    }

    @GetMapping
    public ResponseEntity<List<Object>> getPushNotifications(@RequestParam String subscriptionToken) {
        var notifications = pushNotificationQueue.popAll(subscriptionToken);

        return ResponseEntity.ok(notifications.stream()
                .map(n -> mapNotificationMessage(n.getMessage()))
                .toList());
    }

    private Object mapNotificationMessage(String message) {
        try {
            return objectMapper.readValue(message, Object.class);
        } catch (Exception e) {
            return message;
        }
    }
}
