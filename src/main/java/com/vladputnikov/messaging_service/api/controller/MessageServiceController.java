package com.vladputnikov.messaging_service.api.controller;

import com.vladputnikov.messaging_service.persistent.dto.MessageDto;
import com.vladputnikov.messaging_service.persistent.model.Message;
import com.vladputnikov.messaging_service.persistent.util.MessageStatus;
import com.vladputnikov.messaging_service.service.NotificationService;
import com.vladputnikov.messaging_service.util.mapper.MessageMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Message controller", description = "API for working with message service")
@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageServiceController {
    private final MessageMapper mapper;
    private final NotificationService emailNotificationService;

    @PostMapping("/sendNotificationMessage")
    @Operation(summary = "Send a message", description = "Sending a message")
    public ResponseEntity<Long> sendNotificationMessage(@RequestBody MessageDto messageDto) {
        log.info("Sending a message");
        Message savedMessage = mapper.fromMessageDtoToMessage(messageDto);
        emailNotificationService.sendMessage(savedMessage);
        log.info("Message sent");
        return new ResponseEntity<>(savedMessage.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/message")
    @Operation(summary = "Get a message", description = "Get a specific message by id")
    public ResponseEntity<MessageDto> getMessage(@PathVariable Long id) {
        log.info("Getting a message");
        Message receivedMessage = emailNotificationService.getMessageById(id);
        log.info("Message received");
        MessageDto messageDto = mapper.fromMessageToMessageDto(receivedMessage);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @GetMapping("/{id}/status")
    @Operation(summary = "Get status", description = "Getting status of a message by id")
    public ResponseEntity<MessageStatus> getMessageStatusById(@PathVariable Long id) {
        log.info("Getting a message status");
        return new ResponseEntity<>(emailNotificationService.getMessageStatusById(id), HttpStatus.OK);
    }


    @GetMapping("/messages/{subject}")
    @Operation(summary = "Get all messages by subject", description = "Getting all messages by subject")
    public ResponseEntity<List<Message>> getMessagesBySubject(@PathVariable String subject) {
        log.info("Getting all messages by subject");
        return new ResponseEntity<>(emailNotificationService.getAllMessagesBySubject(subject), HttpStatus.OK);
    }
}
