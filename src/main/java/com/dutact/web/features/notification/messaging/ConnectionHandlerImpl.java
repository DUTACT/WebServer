package com.dutact.web.features.notification.messaging;

import com.dutact.web.features.notification.messaging.exceptions.TokenAlreadyConnectException;
import com.dutact.web.features.notification.websocket.SSPRSession;
import org.springframework.stereotype.Component;

@Component
public class ConnectionHandlerImpl implements ConnectionHandler {
    private final ConnectionRegistry connectionRegistry;

    public ConnectionHandlerImpl(ConnectionRegistry connectionRegistry) {
        this.connectionRegistry = connectionRegistry;
    }

    @Override
    public void connect(SSPRSession session, String subscriptionToken) throws TokenAlreadyConnectException {
        connectionRegistry.addConnection(session, subscriptionToken);
    }

    @Override
    public void disconnect(String subscriptionToken) {
        connectionRegistry.removeConnection(subscriptionToken);
    }
}
