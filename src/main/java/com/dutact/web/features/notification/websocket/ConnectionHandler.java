package com.dutact.web.features.notification.websocket;

public interface ConnectionHandler {
    void connect(SSPRSession session, String token);

    void disconnect(SSPRSession session);
}
