package com.dutact.web.features.notification.infrastructure.push.exceptions;

public class InvalidSubscriptionTokenException extends RuntimeException {
    public InvalidSubscriptionTokenException() {
        super("Invalid subscription token");
    }
}
