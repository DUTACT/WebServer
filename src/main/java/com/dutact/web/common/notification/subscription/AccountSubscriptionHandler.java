package com.dutact.web.common.notification.subscription;

public interface AccountSubscriptionHandler {
    /**
     * @return subscription token
     */
    String subscribe(String deviceId, Integer accountId);

    void unsubscribe(String subscriptionToken);
}
