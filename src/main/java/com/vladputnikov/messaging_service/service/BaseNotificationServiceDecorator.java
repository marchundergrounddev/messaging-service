package com.vladputnikov.messaging_service.service;

import com.vladputnikov.messaging_service.persistent.model.Message;
import com.vladputnikov.messaging_service.persistent.util.MessageStatus;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Setter
public class BaseNotificationServiceDecorator implements NotificationService {

    private final NotificationService service;

    public BaseNotificationServiceDecorator(NotificationService service) {
        this.service = service;
    }

    @Override
    public CompletableFuture<Void> sendMessage(Message message) {
        log.info("Sending a message");
        return service.sendMessage(message);
    }

    @Override
    public Message getMessageById(Long id) {
        return service.getMessageById(id);
    }

    @Override
    public List<Message> getAllMessagesBySubject(String subject) {
        return service.getAllMessagesBySubject(subject);
    }

    @Override
    public MessageStatus getMessageStatusById(Long id) {
        return service.getMessageStatusById(id);
    }
}
