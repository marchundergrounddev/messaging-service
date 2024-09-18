package com.vladputnikov.messaging_service.service;

import com.vladputnikov.messaging_service.persistent.model.Message;
import com.vladputnikov.messaging_service.persistent.util.MessageStatus;
import com.vladputnikov.messaging_service.persistent.util.MessageType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailNotificationService extends BaseNotificationServiceDecorator {
    private final JavaMailSender mailSender;

    public EmailNotificationService(NotificationService notificationServiceImpl, JavaMailSender mailSender) {
        super(notificationServiceImpl);
        this.mailSender = mailSender;
    }

    @Override
    public void sendMessage(Message message) {
        System.out.println("Email logic");
        sendAsyncMessage(message);
        super.sendMessage(message);
    }

    void sendAsyncMessage(Message message) {
        message.setMessageSentAt(LocalDateTime.now());
        updateMessageStatus(MessageStatus.PENDING, message);

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                mailSender.send(createMessage(message));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                updateMessageStatus(MessageStatus.FAILED, message);
                System.err.println("Error sending message: " + exception.getMessage());
            } else {
                updateMessageStatus(MessageStatus.SUCCESSFUL, message);
            }
        });
    }

    private SimpleMailMessage createMessage(Message message) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        message.setMessageType(MessageType.EMAIL);
        emailMessage.setTo(message.getRecipient());
        emailMessage.setSubject(message.getSubject());
        emailMessage.setText(message.getText());
        return emailMessage;
    }
}
