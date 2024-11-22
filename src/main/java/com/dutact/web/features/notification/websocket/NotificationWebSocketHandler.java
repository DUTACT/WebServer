package com.dutact.web.features.notification.websocket;

import com.dutact.web.features.notification.messaging.ConnectionHandler;
import com.dutact.web.features.notification.messaging.exceptions.TokenAlreadyConnectException;
import com.dutact.web.features.notification.subscription.AccountSubscriptionRegistry;
import jakarta.annotation.Nonnull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import static com.dutact.web.features.notification.websocket.SSPRMessageCommand.*;

@Log4j2
@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {
    private final SSPRMessageMapper SSPRMessageMapper;
    private final AccountSubscriptionRegistry subscriptionRegistry;
    private final ConnectionHandler connectionHandler;

    public NotificationWebSocketHandler(SSPRMessageMapper SSPRMessageMapper,
                                        AccountSubscriptionRegistry subscriptionRegistry,
                                        ConnectionHandler connectionHandler) {
        super();
        this.SSPRMessageMapper = SSPRMessageMapper;
        this.subscriptionRegistry = subscriptionRegistry;
        this.connectionHandler = connectionHandler;
    }

    @Override
    protected void handleTextMessage(@Nonnull WebSocketSession session, @Nonnull TextMessage message) throws Exception {
        var payload = message.getPayload();

        var ssprMessage = SSPRMessageMapper.toSSPRMessage(payload);
        var ssprSession = new SSPRWebsocketSession(session, SSPRMessageMapper);

        try {
            var response = switch (ssprMessage.getCommand()) {
                case SUBSCRIBE -> handleSubscribe(ssprMessage);
                case UNSUBSCRIBE -> handleUnsubscribe(ssprMessage);
                case CONNECT -> handleConnect(ssprSession, ssprMessage);
                case DISCONNECT -> handleDisconnect(ssprMessage);
                default -> unsupportedCommand();
            };

            ssprSession.send(response);
        } catch (Exception e) {
            log.error("Error handling message", e);

            var response = new SSPRMessage();
            response.setCommand(ERROR);
            response.setBody("Something went wrong: " + e.getMessage());

            ssprSession.send(response);
        }

    }

    private SSPRMessage handleSubscribe(SSPRMessage ssprMessage) {
        var headers = ssprMessage.getHeaders();
        var deviceId = headers.get("device-id");
        var accountId = Integer.parseInt(headers.get("account-id"));
        var subscriptionToken = subscriptionRegistry.subscribe(deviceId, accountId);

        var response = new SSPRMessage();
        response.setCommand(OK);
        response.getHeaders().put("subscription-token", subscriptionToken);

        return response;
    }

    private SSPRMessage handleUnsubscribe(SSPRMessage ssprMessage) {
        var subscriptionToken = ssprMessage.getHeaders().get("subscription-token");
        subscriptionRegistry.unsubscribe(subscriptionToken);

        var response = new SSPRMessage();
        response.setCommand(OK);

        return response;
    }

    private SSPRMessage handleConnect(SSPRSession session, SSPRMessage ssprMessage) {
        var headers = ssprMessage.getHeaders();
        var subscriptionToken = headers.get("subscription-token");
        try {
            connectionHandler.connect(session, subscriptionToken);
        } catch (TokenAlreadyConnectException e) {
            var response = new SSPRMessage();
            response.setCommand(ERROR);
            response.setBody("Token already connected");

            return response;
        }

        var response = new SSPRMessage();
        response.setCommand(OK);

        return response;
    }

    private SSPRMessage handleDisconnect(SSPRMessage ssprMessage) {
        var subscriptionToken = ssprMessage.getHeaders().get("subscription-token");
        connectionHandler.disconnect(subscriptionToken);

        var response = new SSPRMessage();
        response.setCommand(OK);

        return response;
    }

    private SSPRMessage unsupportedCommand() {
        var response = new SSPRMessage();
        response.setCommand(ERROR);
        response.setBody("Unsupported command");

        return response;
    }
}
