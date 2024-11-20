package com.dutact.web.features.notification.websocket;

import lombok.Data;

@Data
public class SubscriptionInfo {
    private String accessToken;
    private String deviceId;
}
