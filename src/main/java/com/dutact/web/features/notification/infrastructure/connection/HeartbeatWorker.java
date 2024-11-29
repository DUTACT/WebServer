package com.dutact.web.features.notification.infrastructure.connection;

import com.dutact.web.features.notification.infrastructure.websocket.SSPRMessage;
import com.dutact.web.features.notification.infrastructure.websocket.SSPRMessageCommand;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
public class HeartbeatWorker {
    private final Map<String, Long> failedConnections = new HashMap<>();
    private final ConnectionRegistry connectionRegistry;

    @Value("${notification.heartbeat.max-retries}")
    private int maxRetries;

    public HeartbeatWorker(ConnectionRegistry connectionRegistry) {
        this.connectionRegistry = connectionRegistry;
    }

    @Scheduled(fixedDelayString = "${notification.heartbeat.interval-secs}", timeUnit = TimeUnit.SECONDS)
    public void sendHeartbeat() {
        var sessions = connectionRegistry.getSessions();

        failedConnections.keySet()
                .removeIf(sessionId ->
                        sessions.stream().noneMatch(
                                session -> session.getId().equals(sessionId)));

        for (var session : sessions) {
            var sessionId = session.getId();
            if (failedConnections.containsKey(sessionId)) {
                var retries = failedConnections.get(sessionId);
                if (retries >= maxRetries) {
                    connectionRegistry.removeConnection(sessionId);
                    failedConnections.remove(sessionId);
                    continue;
                }
                failedConnections.put(sessionId, retries + 1);
            }
            try {
                var message = new SSPRMessage();
                message.setCommand(SSPRMessageCommand.PING);
                session.send(message);
            } catch (Exception e) {
                failedConnections.put(sessionId, 1L);
                log.debug("Failed to send heartbeat to session {}", sessionId);
            }
        }
    }
}
