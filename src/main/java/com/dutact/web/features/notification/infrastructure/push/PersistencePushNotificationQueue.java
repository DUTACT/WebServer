package com.dutact.web.features.notification.infrastructure.push;

import com.dutact.web.features.notification.infrastructure.push.data.PushNotificationRepository;
import com.dutact.web.features.notification.infrastructure.push.data.PushNotificationSpecs;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class PersistencePushNotificationQueue implements PushNotificationQueue {
    private final PushNotificationRepository pushNotificationRepository;

    public PersistencePushNotificationQueue(PushNotificationRepository pushNotificationRepository) {
        this.pushNotificationRepository = pushNotificationRepository;
    }

    @Override
    public void push(List<PushNotification> pushNotifications) {
        var pushNotificationEntities = pushNotifications.stream()
                .map(pushNotification -> {
                    var entity = new com.dutact.web.features.notification.infrastructure.push.data.PushNotification();
                    entity.setSubscriptionToken(pushNotification.getSubscriptionToken());
                    entity.setMessage(pushNotification.getMessage());
                    return entity;
                })
                .toList();

        pushNotificationRepository.saveAll(pushNotificationEntities);
    }

    @Override
    @Transactional
    public List<PushNotification> popAll(String subscriptionToken) {
        var pushNotificationEntities = pushNotificationRepository
                .findAll(PushNotificationSpecs.hasSubscriptionToken(subscriptionToken));
        pushNotificationRepository.deleteAll(pushNotificationEntities);

        return pushNotificationEntities.stream()
                .sorted(Comparator.comparing(com.dutact.web.features.notification.infrastructure.push.data.PushNotification::getId))
                .map(pushNotificationEntity -> {
                    var notification = new PushNotification();
                    notification.setSubscriptionToken(pushNotificationEntity.getSubscriptionToken());
                    notification.setMessage(pushNotificationEntity.getMessage());

                    return notification;
                })
                .toList();
    }
}
