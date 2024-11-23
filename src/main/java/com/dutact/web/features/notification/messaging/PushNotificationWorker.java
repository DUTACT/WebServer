package com.dutact.web.features.notification.messaging;

import java.util.Collection;

public interface PushNotificationWorker {
    void sendNotification(Collection<String> subscriptionTokens, String message);
}
