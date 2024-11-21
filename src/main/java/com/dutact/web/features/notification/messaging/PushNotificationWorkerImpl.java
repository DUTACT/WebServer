package com.dutact.web.features.notification.messaging;

import com.dutact.web.features.notification.subscription.AccountSubscriptionRegistry;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Log4j2
@Component
public class PushNotificationWorkerImpl implements PushNotificationWorker {
    private final AccountSubscriptionRegistry subscriptionRegistry;
    private final MessageSender messageSender;

    public PushNotificationWorkerImpl(AccountSubscriptionRegistry subscriptionRegistry,
                                      MessageSender messageSender) {
        this.subscriptionRegistry = subscriptionRegistry;
        this.messageSender = messageSender;
    }

    @Override
    public void sendNotification(Collection<Integer> userIds, String message) {
        var subscriptions = subscriptionRegistry.getSubscriptions(userIds);
        for (var subscription : subscriptions) {
            try {
                messageSender.sendMessage(subscription.getSubscriptionToken(), message);
            } catch (Exception e) {
                log.error("Failed to send message to subscription {}", subscription.getSubscriptionToken(), e);
            }
        }
    }
}
