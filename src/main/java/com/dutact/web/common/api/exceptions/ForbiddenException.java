package com.dutact.web.common.api.exceptions;

public class ForbiddenException extends Exception {
    public ForbiddenException() {
        super("Forbidden");
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
