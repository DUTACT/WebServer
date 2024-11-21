package com.dutact.web.features.notification.websocket;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SSPRMessageCommand {
    public static final String OK = "OK";
    public static final String ERROR = "ERROR";
    public static final String SUBSCRIBE = "SUBSCRIBE";
    public static final String UNSUBSCRIBE = "UNSUBSCRIBE";
    public static final String MESSAGE = "MESSAGE";
}
