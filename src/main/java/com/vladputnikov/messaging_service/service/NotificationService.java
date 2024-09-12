package com.vladputnikov.messaging_service.service;

import com.vladputnikov.messaging_service.persistent.model.Message;
import com.vladputnikov.messaging_service.persistent.util.MessageStatus;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface NotificationService {

    CompletableFuture<Void> sendMessage(Message message);

    Message getMessageById(Long id);

    List<Message> getAllMessagesBySubject(String subject);

    MessageStatus getMessageStatusById(Long id);
}
