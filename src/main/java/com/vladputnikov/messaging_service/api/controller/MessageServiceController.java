package com.vladputnikov.messaging_service.api.controller;

import com.vladputnikov.messaging_service.persistent.dto.MessageDto;
import com.vladputnikov.messaging_service.persistent.model.Message;
import com.vladputnikov.messaging_service.persistent.util.MessageStatus;
import com.vladputnikov.messaging_service.service.EmailNotificationService;
import com.vladputnikov.messaging_service.service.NotificationService;
import com.vladputnikov.messaging_service.service.NotificationServiceImpl;
import com.vladputnikov.messaging_service.util.mapper.MessageMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Message controller", description = "API for working with message service")
@RestController
@Slf4j
@RequestMapping("/api")
public class MessageServiceController {
    private final MessageMapper mapper;
    private final NotificationService notificationService;

    public MessageServiceController(MessageMapper mapper, NotificationService notificationService) {
        this.mapper = mapper;
        this.notificationService = notificationService;
    }

    @PostMapping("/sendNotificationMessage")
    @Operation(summary = "Send a message", description = "Sending a message")
    public ResponseEntity<Long> sendNotificationMessage(@RequestBody MessageDto messageDto) {
        log.info("Sending a message");
        NotificationService service = new EmailNotificationService(notificationService);
        Message savedMessage = mapper.fromMessageDtoToMessage(messageDto);
        service.sendMessage(savedMessage);
        log.info("Message sent");
        return new ResponseEntity<>(savedMessage.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/message")
    @Operation(summary = "Get a message", description = "Get a specific message by id")
    public ResponseEntity<MessageDto> getMessage(@PathVariable Long id) {
        log.info("Getting a message");
        Message receivedMessage = notificationService.getMessageById(id);
        log.info("Message received");
        MessageDto messageDto = mapper.fromMessageToMessageDto(receivedMessage);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @GetMapping("/{id}/status")
    @Operation(summary = "Get status", description = "Getting status of a message by id")
    public ResponseEntity<MessageStatus> getMessageStatus(@PathVariable Long id) {
        log.info("Getting a message status");
        return new ResponseEntity<>(notificationService.getMessageStatusById(id), HttpStatus.OK);
    }


    @GetMapping("/messages/{subject}")
    @Operation(summary = "Get all messages by subject", description = "Getting all messages by subject")
    public ResponseEntity<List<Message>> getMessageStatus(@PathVariable String subject) {
        log.info("Getting all messages by subject");
        return new ResponseEntity<>(notificationService.getAllMessagesBySubject(subject), HttpStatus.OK);
    }
}
