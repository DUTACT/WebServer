package com.dutact.web.features.notification.infrastructure.websocket;

import com.dutact.web.features.notification.infrastructure.push.exceptions.NotConnectedException;

public interface MessageSender {
    boolean isConnected(String subscriptionToken);

    void sendMessage(String subscriptionToken, String message) throws NotConnectedException;
}
