package com.dutact.web.features.notification.infrastructure.push.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PushNotificationRepository extends
        JpaRepository<PushNotification, Integer>, JpaSpecificationExecutor<PushNotification> {
}
