package com.dutact.web.features.notification.subscription;

public interface AccountSubscriptionHandler {
    /**
     * @return subscription token
     */
    String subscribe(String deviceId, Integer accountId);

    void unsubscribe(String subscriptionToken);
}
