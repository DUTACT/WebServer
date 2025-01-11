package com.dutact.web.common.notification.infrastructure.push;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PushNotification {
    private String message;
    private String subscriptionToken;
    private LocalDateTime expireAt;
}
