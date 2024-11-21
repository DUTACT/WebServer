package com.dutact.web.features.notification.messaging;

import java.util.Collection;

public interface PushNotificationWorker {
    void sendNotification(Collection<Integer> userIds, String message);
}
