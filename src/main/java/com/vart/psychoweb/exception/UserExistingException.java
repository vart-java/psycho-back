package com.vart.psychoweb.exception;

public class UserExistingException extends RuntimeException {
    public UserExistingException(String message) {
        super(message);
    }
}
