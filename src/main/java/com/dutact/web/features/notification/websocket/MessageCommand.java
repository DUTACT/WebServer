package com.dutact.web.features.notification.websocket;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageCommand {
    public static final String SUBSCRIBE = "SUBSCRIBE";
    public static final String UNSUBSCRIBE = "UNSUBSCRIBE";
}
