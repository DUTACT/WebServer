package com.dutact.web.common.notification.infrastructure.connection;

import com.dutact.web.common.notification.infrastructure.push.exceptions.TokenAlreadyConnectException;
import com.dutact.web.common.notification.infrastructure.websocket.SSPRSession;
import jakarta.annotation.Nullable;

import java.util.Collection;

public interface ConnectionRegistry {
    void addConnection(SSPRSession session, String subscriptionToken) throws TokenAlreadyConnectException;

    void removeConnection(String subscriptionToken);

    @Nullable
    SSPRSession getSession(String subscriptionToken);

    Collection<SSPRSession> getSessions();
}
