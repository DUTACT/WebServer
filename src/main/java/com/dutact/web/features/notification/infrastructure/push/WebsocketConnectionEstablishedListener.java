package com.dutact.web.features.notification.infrastructure.push;

import com.dutact.web.features.notification.infrastructure.connection.ConnectionEstablishedEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WebsocketConnectionEstablishedListener {
    private final WebsocketPushNotificationHandler pushNotificationHandler;

    @Async
    @EventListener
    public void onConnectionEstablished(ConnectionEstablishedEvent event) {
        pushNotificationHandler.push(event.subscriptionToken());
    }
}
