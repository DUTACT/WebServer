package com.dutact.web.features.notification.infrastructure.connection;

import com.dutact.web.features.notification.infrastructure.websocket.SSPRSession;

public interface ConnectionHandler {
    void connect(SSPRSession session, String subscriptionToken);

    void disconnect(String subscriptionToken);
}
