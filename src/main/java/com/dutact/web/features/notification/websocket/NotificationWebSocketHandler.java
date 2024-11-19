package com.dutact.web.features.notification.websocket;

import jakarta.annotation.Nonnull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

public class NotificationWebSocketHandler extends TextWebSocketHandler {
    private Map<String, WebSocketSession> sessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (sessions.get(session.getId()) == null) {
            sessions.put(session.getId(), session);
            System.out.println("New session added: " + session.getId());
        } else {
            System.out.println("Session already exists: " + session.getId());
        }
    }

    @Override
    protected void handleTextMessage(@Nonnull WebSocketSession session, @Nonnull TextMessage message) throws Exception {
        var payload = message.getPayload();

        System.out.println("Received message: " + payload);

        for (var s : sessions.values()) {
            System.out.println("Cur session open: " + s.isOpen());
            if (!s.getId().equals(session.getId())) {
                s.sendMessage(new TextMessage(String.format("%s: %s", session.getId(), payload)));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @Nonnull CloseStatus status) throws Exception {
        sessions.remove(session.getId()).close();
        System.out.println("Session closed: " + session.getId());
        System.out.println("Status: " + status);
        System.out.println("Is open: " + session.isOpen());
    }
}
