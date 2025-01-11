package com.dutact.web.common.notification.infrastructure.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class SSPRWebsocketSession implements SSPRSession {
    private final SSPRMessageMapper messageMapper;
    private final WebSocketSession session;

    public SSPRWebsocketSession(WebSocketSession session, SSPRMessageMapper messageMapper) {
        this.session = session;
        this.messageMapper = messageMapper;
    }

    @Override
    public void send(SSPRMessage message) throws IOException {
        session.sendMessage(new TextMessage(messageMapper.SSPRMessageToString(message)));
    }

    @Override
    public void close() throws IOException {
        session.close();
    }

    @Override
    public boolean isOpen() {
        return session.isOpen();
    }

    @Override
    public String getId() {
        return session.getId();
    }
}
