package com.vladputnikov.messaging_service.api.exception;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(String message) { super(message); }
}
