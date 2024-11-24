package com.dutact.web.features.notification.subscription;

public interface AccountSubscriptionHandler {
    /**
     * @return subscription token
     */
    String subscribe(String deviceId, String accessToken);

    void unsubscribe(String subscriptionToken);
}
