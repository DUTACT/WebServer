package com.dutact.web.features.notification.messaging.exceptions;

public class TokenAlreadyConnectException extends Exception {
    public TokenAlreadyConnectException() {
        super("Token already connected");
    }
}
