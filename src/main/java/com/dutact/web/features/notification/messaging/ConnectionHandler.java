package com.dutact.web.features.notification.messaging;

import com.dutact.web.features.notification.messaging.exceptions.TokenAlreadyConnectException;
import com.dutact.web.features.notification.websocket.SSPRSession;

public interface ConnectionHandler {
    void connect(SSPRSession session, String subscriptionToken) throws TokenAlreadyConnectException;

    void disconnect(String subscriptionToken);
}
