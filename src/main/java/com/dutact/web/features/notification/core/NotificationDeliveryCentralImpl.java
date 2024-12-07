package com.dutact.web.features.notification.core;

import com.dutact.web.common.mapper.ObjectMapperUtils;
import com.dutact.web.features.notification.infrastructure.push.PushNotification;
import com.dutact.web.features.notification.infrastructure.push.PushNotificationQueue;
import com.dutact.web.features.notification.infrastructure.push.event.NotificationPushedEvent;
import com.dutact.web.features.notification.subscription.data.AccountSubscription;
import com.dutact.web.features.notification.subscription.data.AccountSubscriptionRepository;
import com.dutact.web.features.notification.subscription.data.AccountSubscriptionSpecs;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
public class NotificationDeliveryCentralImpl implements NotificationDeliveryCentral {
    private final ObjectMapper objectMapper = ObjectMapperUtils.createObjectMapper();
    private final AccountSubscriptionRepository subscriptionRepository;
    private final NotificationRepository notificationRepository;
    private final PushNotificationMapper pushNotificationMapper;
    private final PushNotificationQueue pushNotificationQueue;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${notification.push.lifespan-secs.default}")
    private int defaultPushNotificationLifespanSecs;

    public NotificationDeliveryCentralImpl(AccountSubscriptionRepository subscriptionRepository,
                                           NotificationRepository notificationRepository,
                                           PushNotificationMapper pushNotificationMapper,
                                           PushNotificationQueue pushNotificationQueue,
                                           ApplicationEventPublisher eventPublisher) {
        this.subscriptionRepository = subscriptionRepository;
        this.notificationRepository = notificationRepository;
        this.pushNotificationMapper = pushNotificationMapper;
        this.pushNotificationQueue = pushNotificationQueue;
        this.eventPublisher = eventPublisher;
    }

    @SneakyThrows
    @Override
    public void sendNotification(NotificationData data) {
        var notifications = getNotifications(data);
        notificationRepository.saveAll(notifications);

        var pushNotifications = getPushNotifications(notifications);
        pushNotificationQueue.push(pushNotifications);

        var subscriptionTokens = pushNotifications.stream()
                .map(PushNotification::getSubscriptionToken)
                .collect(Collectors.toSet());

        for (var subscriptionToken : subscriptionTokens) {
            eventPublisher.publishEvent(new NotificationPushedEvent(subscriptionToken));
        }
    }

    private List<Notification> getNotifications(NotificationData data) {
        return data.getAccountIds().stream()
                .map(accountId -> {
                    var notification = new Notification();
                    notification.setAccountId(accountId);
                    notification.setDetails(data.getDetails());
                    notification.setNotificationType(data.getNotificationType());
                    notification.setExpireAt(data.getExpireAt());

                    return notification;
                })
                .toList();
    }

    private List<PushNotification> getPushNotifications(List<Notification> notifications) {
        var accountIds = notifications.stream()
                .map(Notification::getAccountId)
                .collect(Collectors.toList());

        var accountIdTokenMap = subscriptionRepository
                .findAll(AccountSubscriptionSpecs.hasAccountIdIn(accountIds))
                .stream()
                .collect(Collectors.groupingBy(AccountSubscription::getAccountId,
                        Collectors.mapping(AccountSubscription::getSubscriptionToken, Collectors.toList())));

        var pushNotifications = new ArrayList<PushNotification>();
        for (var notification : notifications) {
            var accountTokens = accountIdTokenMap.get(notification.getAccountId());
            if (accountTokens == null) {
                continue;
            }

            for (var subscriptionToken : accountTokens) {
                try {
                    var message = pushNotificationMapper.toMessage(notification);
                    var pushNotification = new PushNotification();
                    pushNotification.setSubscriptionToken(subscriptionToken);
                    pushNotification.setMessage(objectMapper.writeValueAsString(message));
                    pushNotification.setExpireAt(notification.getExpireAt() != null ?
                            notification.getExpireAt() :
                            notification.getCreatedAt().plusSeconds(defaultPushNotificationLifespanSecs));

                    pushNotifications.add(pushNotification);
                } catch (Exception e) {
                    log.error("Failed to create push notification for notification with id: {}", notification.getId(), e);
                }
            }

        }

        return pushNotifications;
    }
}
