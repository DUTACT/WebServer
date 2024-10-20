package com.dutact.web.common.api.exceptions;

public class NotExistsException extends Exception {
    public NotExistsException() {
        super("The requested resource does not exist");
    }

    public NotExistsException(String message) {
        super(message);
    }
}
