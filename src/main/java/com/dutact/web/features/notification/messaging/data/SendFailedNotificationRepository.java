package com.dutact.web.features.notification.messaging.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SendFailedNotificationRepository
        extends JpaRepository<SendFailedNotification, Integer>,
        JpaSpecificationExecutor<SendFailedNotification> {
}
