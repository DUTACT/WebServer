package com.dutact.web.features.notification.core;

import java.util.Collection;

public interface NotificationDeliveryCentral {
    void sendNotification(Collection<Integer> accountIds, Object details, String notificationType);

}
