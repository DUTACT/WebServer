package com.dutact.web.features.notification.websocket;

import org.springframework.stereotype.Component;

@Component
public class StompMessageMapper {
    public StompMessage toStompMessage(String message) {
        var stompMessage = new StompMessage();
        var parts = message.split("\n\n");
        var commandAndHeaders = parts[0].split("\n");

        stompMessage.setCommand(commandAndHeaders[0]);

        for (var i = 1; i < commandAndHeaders.length; i++) {
            var header = commandAndHeaders[i].split(":");
            stompMessage.getHeaders().put(header[0], header[1]);
        }

        stompMessage.setBody(parts[1]);

        return stompMessage;
    }

    public String fromStompMessage(StompMessage stompMessage) {
        var builder = new StringBuilder();
        builder.append(stompMessage.getCommand()).append("\n");

        for (var entry : stompMessage.getHeaders().entrySet()) {
            builder.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }

        builder.append("\n").append(stompMessage.getBody());

        return builder.toString();
    }
}
