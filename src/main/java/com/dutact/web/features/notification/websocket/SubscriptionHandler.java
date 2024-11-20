package com.dutact.web.features.notification.websocket;

public interface SubscriptionHandler {
    /**
     * @return token
     */
    String subscribe(SubscriptionInfo subscriptionInfo);

    void unsubscribe(String token);
}
