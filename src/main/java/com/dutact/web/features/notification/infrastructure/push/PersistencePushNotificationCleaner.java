package com.dutact.web.features.notification.infrastructure.push;

import com.dutact.web.features.notification.infrastructure.push.data.PushNotificationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Log4j2
@Component
@AllArgsConstructor
public class PersistencePushNotificationCleaner {
    private final PushNotificationRepository pushNotificationRepository;

    @Transactional
    @Scheduled(cron = "${notification.push.cleanup-cron}")
    public void cleanupExpiredPushNotifications() {
        var now = LocalDateTime.now();
        pushNotificationRepository.deleteAllByExpireAtBefore(now);
        log.trace("Deleted expired push notifications");
    }
}
