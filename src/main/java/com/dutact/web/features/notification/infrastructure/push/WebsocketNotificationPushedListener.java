package com.dutact.web.features.notification.infrastructure.push;

import com.dutact.web.features.notification.infrastructure.push.event.NotificationPushedEvent;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WebsocketNotificationPushedListener {
    private final WebsocketPushNotificationHandler pushNotificationHandler;

    @Async
    @EventListener
    @Transactional
    public void onNotificationPushed(NotificationPushedEvent event) {
        pushNotificationHandler.push(event.subscriptionToken());
    }
}
