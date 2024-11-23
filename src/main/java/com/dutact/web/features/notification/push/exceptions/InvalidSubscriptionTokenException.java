package com.dutact.web.features.notification.push.exceptions;

public class InvalidSubscriptionTokenException extends RuntimeException {
    public InvalidSubscriptionTokenException() {
        super("Invalid subscription token");
    }
}
