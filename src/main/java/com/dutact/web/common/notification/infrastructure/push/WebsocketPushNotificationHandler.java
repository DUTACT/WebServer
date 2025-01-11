package com.dutact.web.common.notification.infrastructure.push;

import com.dutact.web.common.notification.infrastructure.websocket.MessageSender;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Log4j2
@Component
@AllArgsConstructor
public class WebsocketPushNotificationHandler {
    private final PushNotificationQueue pushNotificationQueue;
    private final MessageSender messageSender;


    public void push(String subscriptionToken) {
        if (!messageSender.isConnected(subscriptionToken)) {
            log.debug("Subscription token {} is not connected", subscriptionToken);
            return;
        }

        var pushNotifications = pushNotificationQueue.popAll(subscriptionToken);
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
