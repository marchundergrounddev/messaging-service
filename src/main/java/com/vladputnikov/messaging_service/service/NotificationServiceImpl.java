package com.vladputnikov.messaging_service.service;

import com.vladputnikov.messaging_service.api.exception.MessageNotFoundException;
import com.vladputnikov.messaging_service.persistent.model.Message;
import com.vladputnikov.messaging_service.persistent.repository.MessageRepository;
import com.vladputnikov.messaging_service.persistent.util.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final MessageRepository repository;

    public NotificationServiceImpl(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    @Async
    public CompletableFuture<Void> sendMessage(Message message) {
        System.out.println("Basic message sending logic");
        return CompletableFuture.runAsync(() ->
                repository.save(message)
                );
    }

    @Override
    public Message getMessageById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new MessageNotFoundException(String.format("Message not found by this id: %d ", id))
        );
    }

    @Override
    public List<Message> getAllMessagesBySubject(String subject) {
        return repository.getAllMessagesBySubject(subject).orElseThrow(
                () -> new MessageNotFoundException(String.format("Message not found by subject %s ", subject))
        );
    }

    @Override
    public MessageStatus getMessageStatusById(Long id) {
        return repository.getMessageStatusById(id).orElseThrow(
                () -> new MessageNotFoundException(String.format("Message not found by this id: %d ", id))
        );
    }
}
