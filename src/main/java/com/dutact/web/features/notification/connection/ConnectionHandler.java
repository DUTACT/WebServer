package com.dutact.web.features.notification.connection;

import com.dutact.web.features.notification.websocket.SSPRSession;

public interface ConnectionHandler {
    void connect(SSPRSession session, String subscriptionToken);

    void disconnect(String subscriptionToken);
}
