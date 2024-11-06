package com.dutact.web.auth.exception;

public class NoPermissionException extends Exception{
    public NoPermissionException() {
        super("No permission to access this resource");
    }
}
