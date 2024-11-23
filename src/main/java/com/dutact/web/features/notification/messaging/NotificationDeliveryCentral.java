package com.dutact.web.features.notification.messaging;

import java.util.Collection;

public interface NotificationDeliveryCentral {
    void sendNotification(Collection<Integer> accountIds, Object details, String notificationType);
}
