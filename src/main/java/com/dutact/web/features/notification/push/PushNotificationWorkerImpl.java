package com.dutact.web.features.notification.push;

import com.dutact.web.features.notification.connection.ConnectionEstablishedEvent;
import com.dutact.web.features.notification.push.data.SendFailedNotification;
import com.dutact.web.features.notification.push.data.SendFailedNotificationRepository;
import com.dutact.web.features.notification.push.data.SendFailedNotificationSpecs;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Log4j2
@Component
public class PushNotificationWorkerImpl implements PushNotificationWorker {
    private static final int MAX_RETRIES = 3;

    private final MessageSender messageSender;
    private final SendFailedNotificationRepository sendFailedNotificationRepository;

    public PushNotificationWorkerImpl(MessageSender messageSender,
                                      SendFailedNotificationRepository sendFailedNotificationRepository) {
        this.messageSender = messageSender;
        this.sendFailedNotificationRepository = sendFailedNotificationRepository;
    }

    @Override
    public void sendNotification(Collection<String> subscriptionTokens, String message) {
        var sendFailedNotifications = new ArrayList<SendFailedNotification>();
        for (var subscriptionToken : subscriptionTokens) {
            try {
                messageSender.sendMessage(subscriptionToken, message);
            } catch (Exception e) {
                var sendFailedNotification = new SendFailedNotification();
                sendFailedNotification.setSubscriptionToken(subscriptionToken);
                sendFailedNotification.setMessage(message);
                sendFailedNotifications.add(sendFailedNotification);

                log.error("Failed to send message to subscription token {}", subscriptionToken, e);
            }
        }

        sendFailedNotificationRepository.saveAll(sendFailedNotifications);
    }

    @EventListener
    public void onConnectionEstablished(ConnectionEstablishedEvent event) {
        var subscriptionToken = event.subscriptionToken();
        var failedNotifications = sendFailedNotificationRepository
                .findAll(SendFailedNotificationSpecs.hasSubscriptionToken(subscriptionToken));

        var failedNotificationsToUpdate = new ArrayList<SendFailedNotification>();
        var failedNotificationsToDelete = new ArrayList<SendFailedNotification>();
        for (var failedNotification : failedNotifications) {
            try {
                messageSender.sendMessage(subscriptionToken, failedNotification.getMessage());
                failedNotificationsToDelete.add(failedNotification);
            } catch (Exception e) {
                if (failedNotification.getRetries() < MAX_RETRIES) {
                    failedNotification.setRetries(failedNotification.getRetries() + 1);
                    failedNotificationsToUpdate.add(failedNotification);
                } else {
                    failedNotificationsToDelete.add(failedNotification);
                }
                log.error("Failed to resend message to subscription token {}", subscriptionToken, e);
            }
        }

        sendFailedNotificationRepository.saveAll(failedNotificationsToUpdate);
        sendFailedNotificationRepository.deleteAll(failedNotificationsToDelete);
    }
}
