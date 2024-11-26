package com.dutact.web.features.notification.push;

import java.util.Collection;
import java.util.List;

public interface NotificationQueue {
    void push(Collection<String> subscriptionTokens, String message);

    List<String> popAll(String subscriptionToken);
}
