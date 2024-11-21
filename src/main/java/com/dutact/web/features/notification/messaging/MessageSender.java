package com.dutact.web.features.notification.messaging;

import com.dutact.web.features.notification.messaging.exceptions.NotConnectedException;

public interface MessageSender {
    void sendMessage(String subscriptionToken, String message) throws NotConnectedException;
}
