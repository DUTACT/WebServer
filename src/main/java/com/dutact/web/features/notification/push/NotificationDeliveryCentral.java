package com.dutact.web.features.notification.push;

import java.util.Collection;

public interface NotificationDeliveryCentral {
    void sendNotification(Collection<Integer> accountIds, Object details, String notificationType);
}
