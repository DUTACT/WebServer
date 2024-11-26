package com.dutact.web.features.notification.infrastructure.push;

import lombok.Data;

@Data
public class PushNotification {
    private String message;
    private String subscriptionToken;
}
