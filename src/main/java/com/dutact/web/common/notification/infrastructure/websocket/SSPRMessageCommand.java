package com.dutact.web.common.notification.infrastructure.websocket;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SSPRMessageCommand {
    public static final String OK = "OK";
    public static final String SUBSCRIBE_ACK = "SUBSCRIBE_ACK";
    public static final String UNSUBSCRIBE_ACK = "UNSUBSCRIBE_ACK";
    public static final String ERROR = "ERROR";
    public static final String SUBSCRIBE = "SUBSCRIBE";
    public static final String UNSUBSCRIBE = "UNSUBSCRIBE";
    public static final String CONNECT = "CONNECT";
    public static final String DISCONNECT = "DISCONNECT";
    public static final String MESSAGE = "MESSAGE";
    public static final String PING = "PING";
}
