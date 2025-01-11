package com.dutact.web.common.notification.infrastructure.websocket;

import java.io.IOException;

public interface SSPRSession {
    void send(SSPRMessage message) throws IOException;

    void close() throws IOException;

    boolean isOpen();

    String getId();
}
