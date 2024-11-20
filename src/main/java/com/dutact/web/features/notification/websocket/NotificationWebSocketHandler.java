package com.dutact.web.features.notification.websocket;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {
    //TODO: Support concurrent access
    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final StompMessageMapper stompMessageMapper;

    public NotificationWebSocketHandler(StompMessageMapper stompMessageMapper) {
        super();
        this.stompMessageMapper = stompMessageMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        log.debug("Session established: {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @Nonnull CloseStatus status) throws Exception {
        sessions.remove(session.getId()).close();
        System.out.println("Session closed: " + session.getId());
        System.out.println("Status: " + status);
        System.out.println("Is open: " + session.isOpen());
    }

    @Override
    protected void handleTextMessage(@Nonnull WebSocketSession session, @Nonnull TextMessage message) throws Exception {
        var payload = message.getPayload();

        var stompMessage = stompMessageMapper.toStompMessage(payload);

    }

    @Nullable
    private String handleMessageInternal(@Nonnull StompMessage stompMessage) {
        return null;
    }
}
