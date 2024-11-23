package com.dutact.web.features.notification.messaging;

import com.dutact.web.features.notification.messaging.exceptions.InvalidSubscriptionTokenException;
import com.dutact.web.features.notification.messaging.exceptions.TokenAlreadyConnectException;
import com.dutact.web.features.notification.subscription.data.AccountSubscriptionRepository;
import com.dutact.web.features.notification.websocket.SSPRSession;
import org.springframework.stereotype.Component;

@Component
public class ConnectionHandlerImpl implements ConnectionHandler {
    private final ConnectionRegistry connectionRegistry;
    private final AccountSubscriptionRepository subscriptionRepository;

    public ConnectionHandlerImpl(ConnectionRegistry connectionRegistry,
                                 AccountSubscriptionRepository subscriptionRepository) {
        this.connectionRegistry = connectionRegistry;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public void connect(SSPRSession session, String subscriptionToken) throws TokenAlreadyConnectException {
        if (subscriptionRepository.findById(subscriptionToken).isEmpty()) {
            throw new InvalidSubscriptionTokenException();
        }

        connectionRegistry.addConnection(session, subscriptionToken);
    }

    @Override
    public void disconnect(String subscriptionToken) {
        connectionRegistry.removeConnection(subscriptionToken);
    }
}
