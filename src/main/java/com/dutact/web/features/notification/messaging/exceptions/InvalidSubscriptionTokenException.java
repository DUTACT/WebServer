package com.dutact.web.features.notification.messaging.exceptions;

public class InvalidSubscriptionTokenException extends RuntimeException {
    public InvalidSubscriptionTokenException() {
        super("Invalid subscription token");
    }
}
