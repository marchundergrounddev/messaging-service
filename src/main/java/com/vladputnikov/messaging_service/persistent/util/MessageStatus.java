package com.vladputnikov.messaging_service.persistent.util;

import lombok.Getter;

@Getter
public enum MessageStatus {
    QUEUED,
    PENDING,
    SUCCESSFUL,
    FAILED
}
