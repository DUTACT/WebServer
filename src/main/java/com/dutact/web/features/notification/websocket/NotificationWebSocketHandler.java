package com.dutact.web.features.notification.websocket;

import com.dutact.web.features.notification.messaging.ConnectionHandler;
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
        var response = switch (ssprMessage.getCommand()) {
            case SUBSCRIBE -> handleSubscribe(session, ssprMessage);
            case UNSUBSCRIBE -> handleUnsubscribe(session, ssprMessage);
            default -> unsupportedCommand();
        };

        ssprSession.send(response);
    }

    private SSPRMessage handleSubscribe(WebSocketSession session, SSPRMessage ssprMessage) {
        var headers = ssprMessage.getHeaders();
        var deviceId = headers.get("device-id");
        var accountId = Integer.parseInt(headers.get("account-id"));
        var subscriptionToken = subscriptionRegistry.subscribe(deviceId, accountId);

    }

    private SSPRMessage handleUnsubscribe(WebSocketSession session, SSPRMessage ssprMessage) {
        var subscriptionToken = ssprMessage.getSubscriptionToken();
        subscriptionHandler.unsubscribe(subscriptionToken);

        sessions.remove(subscriptionToken);
    }

    private SSPRMessage unsupportedCommand() {
        var response = new SSPRMessage();
        response.setCommand(ERROR);
        response.setBody("Unsupported command");
    }
}
