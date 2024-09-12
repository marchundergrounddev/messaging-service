package com.vladputnikov.messaging_service.service;

import com.vladputnikov.messaging_service.persistent.model.Message;
import com.vladputnikov.messaging_service.persistent.util.MessageType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.concurrent.CompletableFuture;

public class EmailNotificationService extends BaseNotificationServiceDecorator {

    public EmailNotificationService(NotificationService service) {
        super(service);
    }

    @Override
    public CompletableFuture<Void> sendMessage(Message message) {
        System.out.println("Email logic");
        new JavaMailSenderImpl().send(createMessage(message)); //
        return super.sendMessage(message); //async
    }

    private SimpleMailMessage createMessage(Message message) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        message.setMessageType(MessageType.EMAIL);
        emailMessage.setTo(message.getRecipient());
//        emailMessage.setCc(message.getCc().toString());
        emailMessage.setSubject(message.getSubject());
        emailMessage.setText(message.getText());
        return emailMessage;
    }
}
