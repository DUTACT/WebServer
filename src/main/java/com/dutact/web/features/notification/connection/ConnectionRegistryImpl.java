package com.dutact.web.features.notification.connection;

import com.dutact.web.features.notification.messaging.exceptions.TokenAlreadyConnectException;
import com.dutact.web.features.notification.websocket.SSPRSession;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ConnectionRegistryImpl implements ConnectionRegistry {
    private final Map<String, SSPRSession> connections = new HashMap<>();

    @Override
    public void addConnection(SSPRSession session, String subscriptionToken) throws TokenAlreadyConnectException {
        if (connections.containsKey(subscriptionToken)) {
            throw new TokenAlreadyConnectException();
        }

        connections.put(subscriptionToken, session);
    }

    @Override
    public void removeConnection(String subscriptionToken) {
        connections.remove(subscriptionToken);
    }

    @Nullable
    @Override
    public SSPRSession getSession(String subscriptionToken) {
        return connections.get(subscriptionToken);
    }
}
