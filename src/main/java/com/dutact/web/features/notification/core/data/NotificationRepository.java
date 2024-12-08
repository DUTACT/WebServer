package com.dutact.web.features.notification.core.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NotificationRepository
        extends JpaRepository<Notification, Integer>,
        JpaSpecificationExecutor<Notification> {
}
