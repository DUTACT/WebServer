package com.dutact.web.common.notification.infrastructure.push.exceptions;

public class InvalidSubscriptionTokenException extends RuntimeException {
    public InvalidSubscriptionTokenException() {
        super("Invalid subscription token");
    }
}
