package com.dutact.web.features.notification.websocket;

import lombok.Data;

import java.util.Map;

@Data
public class StompMessage {
    private String command;
    private Map<String, String> headers;
    private String body;
}
