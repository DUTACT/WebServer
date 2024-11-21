package com.dutact.web.features.notification.subscription;

import com.dutact.web.features.notification.websocket.SubscriptionInfo;

public interface SubscriptionHandler {
    /**
     * @return subscription token
     */
    String subscribe(SubscriptionInfo subscriptionInfo);

    void unsubscribe(String subscriptionToken);
}
