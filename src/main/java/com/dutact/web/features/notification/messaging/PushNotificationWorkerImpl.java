package com.dutact.web.features.notification.messaging;

import com.dutact.web.features.notification.subscription.data.AccountSubscriptionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Log4j2
@Component
public class PushNotificationWorkerImpl implements PushNotificationWorker {
    private final MessageSender messageSender;

    public PushNotificationWorkerImpl(AccountSubscriptionRepository subscriptionRepository,
                                      MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void sendNotification(Collection<String> subscriptionTokens, String message) {
        for (var subscriptionToken : subscriptionTokens) {
            try {
                messageSender.sendMessage(subscriptionToken, message);
            } catch (Exception e) {
                log.error("Failed to send message to subscription {}", subscriptionToken, e);
            }
        }
    }
}
