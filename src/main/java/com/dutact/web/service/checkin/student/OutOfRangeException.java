package com.dutact.web.service.checkin.student;

public class OutOfRangeException extends Exception {
    public OutOfRangeException() {
        super("Out of check in range");
    }

    public OutOfRangeException(String message) {
        super(message);
    }
}
