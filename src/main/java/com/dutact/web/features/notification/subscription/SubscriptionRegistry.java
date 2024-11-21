package com.dutact.web.features.notification.subscription;

public interface SubscriptionRegistry {
    String subscribe(String accessToken, String deviceId);

    void unsubscribe(String token);
}
