package com.dutact.web.features.notification.messaging;

import com.dutact.web.features.notification.messaging.exceptions.TokenAlreadyConnectException;
import com.dutact.web.features.notification.websocket.SSPRSession;
import jakarta.annotation.Nullable;

public interface ConnectionRegistry {
    void addConnection(SSPRSession session, String subscriptionToken) throws TokenAlreadyConnectException;

    void removeConnection(String subscriptionToken);

    @Nullable
    SSPRSession getSession(String subscriptionToken);
}
