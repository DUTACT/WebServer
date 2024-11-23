package com.dutact.web.features.notification.connection;

import com.dutact.web.features.notification.push.exceptions.TokenAlreadyConnectException;
import com.dutact.web.features.notification.websocket.SSPRSession;

public interface ConnectionHandler {
    void connect(SSPRSession session, String subscriptionToken) throws TokenAlreadyConnectException;

    void disconnect(String subscriptionToken);
}
