package com.dutact.web.features.notification.websocket;

import org.springframework.stereotype.Component;

@Component
public class SSPRMessageMapper {
    public SSPRMessage toSSPRMessage(String message) {
        var ssprMessage = new SSPRMessage();
        var divider = message.indexOf("\n\n");
        var commandAndHeaders = message.substring(0, divider).split("\n");
        var body = message.substring(divider + 2);

        ssprMessage.setCommand(commandAndHeaders[0]);

        for (var i = 1; i < commandAndHeaders.length; i++) {
            var header = commandAndHeaders[i].split(":");
            ssprMessage.getHeaders().put(header[0], header[1]);
        }

        ssprMessage.setBody(body);

        return ssprMessage;
    }

    public String SSPRMessageToString(SSPRMessage message) {
        var builder = new StringBuilder();
        builder.append(message.getCommand()).append("\n");

        for (var entry : message.getHeaders().entrySet()) {
            builder.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }

        builder.append("\n").append(message.getBody());

        return builder.toString();
    }
}
