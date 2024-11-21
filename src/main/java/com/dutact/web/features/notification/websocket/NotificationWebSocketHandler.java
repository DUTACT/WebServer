package com.dutact.web.features.notification.websocket;

import com.dutact.web.features.notification.messaging.ConnectionHandler;
import jakarta.annotation.Nonnull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

import static com.dutact.web.features.notification.websocket.SSPRMessageCommand.SUBSCRIBE;
import static com.dutact.web.features.notification.websocket.SSPRMessageCommand.UNSUBSCRIBE;

@Log4j2
@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {
    //TODO: Support concurrent access
    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final SSPRMessageMapper SSPRMessageMapper;
    private final SubscriptionHandler subscriptionHandler;
    private final ConnectionHandler connectionHandler;

    public NotificationWebSocketHandler(SSPRMessageMapper SSPRMessageMapper,
                                        SubscriptionHandler subscriptionHandler,
                                        ConnectionHandler connectionHandler) {
        super();
        this.SSPRMessageMapper = SSPRMessageMapper;
        this.subscriptionHandler = subscriptionHandler;
        this.connectionHandler = connectionHandler;
    }

    @Override
    protected void handleTextMessage(@Nonnull WebSocketSession session, @Nonnull TextMessage message) throws Exception {
        var payload = message.getPayload();

        var ssprMessage = SSPRMessageMapper.toSSPRMessage(payload);

        switch (ssprMessage.getCommand()) {
            case SUBSCRIBE -> handleSubscribe(session, ssprMessage);
            case UNSUBSCRIBE -> handleUnsubscribe(session, ssprMessage);
            default -> log.warn("Unsupported command {}", ssprMessage.getCommand());
        }
        ;
    }

    private void handleSubscribe(WebSocketSession session, SSPRMessage ssprMessage) {
        var headers = ssprMessage.getHeaders();
        headers.get("Authorization");
        var subscriptionToken = subscriptionHandler.subscribe(subscriptionInfo);

        sessions.put(subscriptionToken, session);
    }

    private void handleUnsubscribe(WebSocketSession session, SSPRMessage ssprMessage) {
        var subscriptionToken = ssprMessage.getSubscriptionToken();
        subscriptionHandler.unsubscribe(subscriptionToken);

        sessions.remove(subscriptionToken);
    }
}
