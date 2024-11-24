package com.dutact.web.features.notification.push;

import java.util.Collection;

public interface PushNotificationWorker {
    void sendNotification(Collection<String> subscriptionTokens, String message);

    void retryFailedNotifications(String subscriptionToken);
}
