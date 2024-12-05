package com.dutact.web.features.checkin.student.services.exceptions;

public class OutOfRangeException extends Exception {
    public OutOfRangeException() {
        super("Out of check in range");
    }

    public OutOfRangeException(String message) {
        super(message);
    }
}
