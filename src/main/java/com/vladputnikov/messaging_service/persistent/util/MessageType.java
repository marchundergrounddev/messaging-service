package com.vladputnikov.messaging_service.persistent.util;

import lombok.Getter;

@Getter
public enum MessageType {
    EMAIL("EMAIL");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }
}
