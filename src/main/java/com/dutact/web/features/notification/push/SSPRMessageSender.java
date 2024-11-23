package com.dutact.web.features.notification.push;

import com.dutact.web.features.notification.connection.ConnectionRegistry;
import com.dutact.web.features.notification.constants.SSPRMessageCommand;
import com.dutact.web.features.notification.push.exceptions.NotConnectedException;
import com.dutact.web.features.notification.websocket.SSPRMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
public class SSPRMessageSender implements MessageSender {
    private final ConnectionRegistry connectionRegistry;

    public SSPRMessageSender(ConnectionRegistry connectionRegistry) {
        this.connectionRegistry = connectionRegistry;
    }

    @Override
    public void sendMessage(String subscriptionToken, String message) throws NotConnectedException {
        var session = connectionRegistry.getSession(subscriptionToken);
        if (session == null || !session.isOpen()) {
            throw new NotConnectedException();
        }

        var ssprMessage = new SSPRMessage();
        ssprMessage.setCommand(SSPRMessageCommand.MESSAGE);
        ssprMessage.setBody(message);

        try {
            session.send(ssprMessage);
        } catch (IOException e) {
            log.error("Failed to send message to session {}", session.getId(), e);
        }
    }
}
