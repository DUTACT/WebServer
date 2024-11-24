package com.dutact.web.features.notification.push;

import com.dutact.web.features.notification.connection.ConnectionEstablishedEvent;
import jakarta.transaction.Transactional;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PushNotificationWorkerEventListener {
    private final PushNotificationWorker worker;

    public PushNotificationWorkerEventListener(PushNotificationWorker worker) {
        this.worker = worker;
    }

    @Async
    @EventListener
    @Transactional
    public void onConnectionEstablished(ConnectionEstablishedEvent event) {
        worker.retryFailedNotifications(event.subscriptionToken());
    }
}
