package com.dutact.web.features.notification.subscription;

import com.dutact.web.features.notification.websocket.SubscriptionInfo;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionHandlerImpl implements SubscriptionHandler {
    @Override
    public String subscribe(SubscriptionInfo subscriptionInfo) {
        return "";
    }

    @Override
    public void unsubscribe(String subscriptionToken) {

    }
}
