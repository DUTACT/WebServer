package com.dutact.web.common.notification.infrastructure.connection;

import com.dutact.web.common.notification.infrastructure.push.exceptions.InvalidSubscriptionTokenException;
import com.dutact.web.common.notification.infrastructure.push.exceptions.TokenAlreadyConnectException;
import com.dutact.web.common.notification.infrastructure.websocket.SSPRSession;
import com.dutact.web.common.notification.subscription.data.AccountSubscriptionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ConnectionHandlerImpl implements ConnectionHandler {
    private final ConnectionRegistry connectionRegistry;
    private final AccountSubscriptionRepository subscriptionRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ConnectionHandlerImpl(ConnectionRegistry connectionRegistry,
                                 AccountSubscriptionRepository subscriptionRepository,
                                 ApplicationEventPublisher eventPublisher) {
        this.connectionRegistry = connectionRegistry;
        this.subscriptionRepository = subscriptionRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void connect(SSPRSession session, String subscriptionToken) {
        if (subscriptionRepository.findById(subscriptionToken).isEmpty()) {
            throw new InvalidSubscriptionTokenException();
        }

        try {
            connectionRegistry.addConnection(session, subscriptionToken);
        } catch (TokenAlreadyConnectException e) {
            connectionRegistry.removeConnection(subscriptionToken);
            try {
                connectionRegistry.addConnection(session, subscriptionToken);
            } catch (TokenAlreadyConnectException ex) {
                throw new RuntimeException(ex);
            }
        }

        eventPublisher.publishEvent(new ConnectionEstablishedEvent(subscriptionToken));
    }

    @Override
    public void disconnect(String subscriptionToken) {
        connectionRegistry.removeConnection(subscriptionToken);
    }
}
