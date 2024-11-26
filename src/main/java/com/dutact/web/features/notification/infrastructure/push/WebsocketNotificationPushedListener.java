package com.dutact.web.features.notification.infrastructure.push;

import com.dutact.web.features.notification.infrastructure.push.event.NotificationPushedEvent;
import com.dutact.web.features.notification.infrastructure.websocket.MessageSender;
import jakarta.transaction.Transactional;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class WebsocketNotificationPushedListener {
    private final PushNotificationQueue pushNotificationQueue;
    private final MessageSender messageSender;

    public WebsocketNotificationPushedListener(PushNotificationQueue pushNotificationQueue,
                                               MessageSender messageSender) {
        this.pushNotificationQueue = pushNotificationQueue;
        this.messageSender = messageSender;
    }

    @Async
    @EventListener
    @Transactional
    public void onNotificationPushed(NotificationPushedEvent event) {
        if (!messageSender.isConnected(event.subscriptionToken())) {
            return;
        }

        var pushNotifications = pushNotificationQueue.popAll(event.subscriptionToken());
        var failedPushNotifications = new ArrayList<PushNotification>();
        for (var pushNotification : pushNotifications) {
            try {
                messageSender.sendMessage(pushNotification.getSubscriptionToken(), pushNotification.getMessage());
            } catch (Exception e) {
                failedPushNotifications.add(pushNotification);
            }
        }

        if (!failedPushNotifications.isEmpty()) {
            pushNotificationQueue.push(failedPushNotifications);
        }
    }
}
