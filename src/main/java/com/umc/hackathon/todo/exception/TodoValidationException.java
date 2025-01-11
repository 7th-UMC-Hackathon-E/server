package com.umc.hackathon.todo.exception;

public class TodoValidationException extends RuntimeException {

    public TodoValidationException(String message) {
        super(message);
    }

    public TodoValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}