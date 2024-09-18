package com.vladputnikov.messaging_service.service;

import com.vladputnikov.messaging_service.api.exception.MessageNotFoundException;
import com.vladputnikov.messaging_service.persistent.model.Message;
import com.vladputnikov.messaging_service.persistent.repository.MessageRepository;
import com.vladputnikov.messaging_service.persistent.util.MessageStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final MessageRepository repository;

    public NotificationServiceImpl(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void sendMessage(Message message) {
        System.out.println("Basic message sending logic");
        repository.save(message);
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

    @Override
    @Transactional
    public void updateMessageStatus(MessageStatus status, Message message) {
        Map<MessageStatus, Consumer<Message>> map = Map.of(
                MessageStatus.PENDING, m -> {},
                MessageStatus.FAILED, m -> message.setMessageFailedAt(LocalDateTime.now()),
                MessageStatus.SUCCESSFUL, m -> message.setMessageDeliveredAt(LocalDateTime.now()));

        message.setMessageStatus(status);
        map.getOrDefault(status, m -> {}).accept(message);
        repository.save(message);
    }
}
