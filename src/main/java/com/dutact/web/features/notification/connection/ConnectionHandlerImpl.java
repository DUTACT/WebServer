package com.dutact.web.features.notification.connection;

import com.dutact.web.features.notification.push.exceptions.InvalidSubscriptionTokenException;
import com.dutact.web.features.notification.push.exceptions.TokenAlreadyConnectException;
import com.dutact.web.features.notification.subscription.data.AccountSubscriptionRepository;
import com.dutact.web.features.notification.websocket.SSPRSession;
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
    public void connect(SSPRSession session, String subscriptionToken) throws TokenAlreadyConnectException {
        if (subscriptionRepository.findById(subscriptionToken).isEmpty()) {
            throw new InvalidSubscriptionTokenException();
        }

        connectionRegistry.addConnection(session, subscriptionToken);

        eventPublisher.publishEvent(new ConnectionEstablishedEvent(subscriptionToken));
    }

    @Override
    public void disconnect(String subscriptionToken) {
        connectionRegistry.removeConnection(subscriptionToken);
    }
}
