package com.dutact.web.common.notification.infrastructure.connection;

import com.dutact.web.common.notification.infrastructure.websocket.SSPRSession;

public interface ConnectionHandler {
    void connect(SSPRSession session, String subscriptionToken);

    void disconnect(String subscriptionToken);
}
