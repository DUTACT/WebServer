package com.dutact.web.common.notification.infrastructure.push;

import java.util.List;

public interface PushNotificationQueue {
    void push(List<PushNotification> pushNotifications);

    List<PushNotification> popAll(String subscriptionToken);
}
