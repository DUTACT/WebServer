package com.dutact.web.common.notification.infrastructure.websocket;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class SSPRMessage {
    private String command;
    private Map<String, String> headers = new HashMap<>();
    private String body;
}
