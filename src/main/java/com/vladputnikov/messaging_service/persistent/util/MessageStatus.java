package com.vladputnikov.messaging_service.persistent.util;

import lombok.Getter;

@Getter
public enum MessageStatus {
    PENDING,
    SUCCESSFUL,
    FAILED
}
