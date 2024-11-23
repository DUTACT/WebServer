package com.dutact.web.features.notification.websocket;

import com.dutact.web.features.notification.connection.ConnectionHandler;
import com.dutact.web.features.notification.subscription.AccountSubscriptionHandler;
import jakarta.annotation.Nonnull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import static com.dutact.web.features.notification.constants.SSPRMessageCommand.*;

@Log4j2
@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {
    private final SSPRMessageMapper SSPRMessageMapper;
    private final AccountSubscriptionHandler accountSubscriptionHandler;
    private final ConnectionHandler connectionHandler;

    public NotificationWebSocketHandler(SSPRMessageMapper SSPRMessageMapper,
                                        AccountSubscriptionHandler accountSubscriptionHandler,
                                        ConnectionHandler connectionHandler) {
        super();
        this.SSPRMessageMapper = SSPRMessageMapper;
        this.accountSubscriptionHandler = accountSubscriptionHandler;
        this.connectionHandler = connectionHandler;
    }

    @Override
    protected void handleTextMessage(@Nonnull WebSocketSession session, @Nonnull TextMessage message) throws Exception {
        var payload = message.getPayload();

        var ssprSession = new SSPRWebsocketSession(session, SSPRMessageMapper);

        var ssprMessage = SSPRMessageMapper.toSSPRMessage(payload);
        var receipt = ssprMessage.getHeaders().get("receipt");

        try {
            var response = switch (ssprMessage.getCommand()) {
                case SUBSCRIBE -> handleSubscribe(ssprMessage);
                case UNSUBSCRIBE -> handleUnsubscribe(ssprMessage);
                case CONNECT -> handleConnect(ssprSession, ssprMessage);
                case DISCONNECT -> handleDisconnect(ssprMessage);
                default -> unsupportedCommand();
            };

            response.getHeaders().put("receipt", receipt);
            ssprSession.send(response);
        } catch (Exception e) {
            log.error("Error handling message", e);

            var response = new SSPRMessage();
            response.setCommand(ERROR);
            response.getHeaders().put("receipt", receipt);
            response.setBody("Something went wrong: " + e.getMessage());

            ssprSession.send(response);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @Nonnull CloseStatus status) throws Exception {
        connectionHandler.disconnect(session.getId());
    }

    private SSPRMessage handleSubscribe(SSPRMessage ssprMessage) {
        var headers = ssprMessage.getHeaders();
        var deviceId = headers.get("device-id");
        var accessToken = headers.get("access-token");
        var subscriptionToken = accountSubscriptionHandler.subscribe(deviceId, accessToken);

        var response = new SSPRMessage();
        response.setCommand(OK);
        response.getHeaders().put("subscription-token", subscriptionToken);

        return response;
    }

    private SSPRMessage handleUnsubscribe(SSPRMessage ssprMessage) {
        var subscriptionToken = ssprMessage.getHeaders().get("subscription-token");
        accountSubscriptionHandler.unsubscribe(subscriptionToken);

        var response = new SSPRMessage();
        response.setCommand(OK);

        return response;
    }

    private SSPRMessage handleConnect(SSPRSession session, SSPRMessage ssprMessage) {
        var headers = ssprMessage.getHeaders();
        var subscriptionToken = headers.get("subscription-token");

        connectionHandler.connect(session, subscriptionToken);

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
