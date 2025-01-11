package com.dutact.web.common.notification.infrastructure.push;

import com.dutact.web.common.notification.infrastructure.push.data.PushNotificationRepository;
import com.dutact.web.common.notification.infrastructure.push.data.PushNotificationSpecs;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Component
public class PersistencePushNotificationQueue implements PushNotificationQueue {
    private final PushNotificationRepository pushNotificationRepository;

    public PersistencePushNotificationQueue(PushNotificationRepository pushNotificationRepository) {
        this.pushNotificationRepository = pushNotificationRepository;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void push(List<PushNotification> pushNotifications) {
        var pushNotificationEntities = pushNotifications.stream()
                .map(pushNotification -> {
                    var entity = new com.dutact.web.common.notification.infrastructure.push.data.PushNotification();
                    entity.setSubscriptionToken(pushNotification.getSubscriptionToken());
                    entity.setMessage(pushNotification.getMessage());
                    entity.setExpireAt(pushNotification.getExpireAt());

                    return entity;
                })
                .toList();

        pushNotificationRepository.saveAll(pushNotificationEntities);
    }

    @Override
    @Transactional
    public List<PushNotification> popAll(String subscriptionToken) {
        var pushNotificationEntities = pushNotificationRepository
                .findAll(PushNotificationSpecs.hasSubscriptionToken(subscriptionToken)
                        .and(PushNotificationSpecs.hasExpireAtAfter(LocalDateTime.now())));
        pushNotificationRepository.deleteAll(pushNotificationEntities);

        return pushNotificationEntities.stream()
                .sorted(Comparator.comparing(com.dutact.web.common.notification.infrastructure.push.data.PushNotification::getId))
                .map(pushNotificationEntity -> {
                    var notification = new PushNotification();
                    notification.setSubscriptionToken(pushNotificationEntity.getSubscriptionToken());
                    notification.setMessage(pushNotificationEntity.getMessage());
                    notification.setExpireAt(pushNotificationEntity.getExpireAt());

                    return notification;
                })
                .toList();
    }
}
