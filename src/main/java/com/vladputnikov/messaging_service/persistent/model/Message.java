package com.vladputnikov.messaging_service.persistent.model;

import com.vladputnikov.messaging_service.persistent.util.MessageStatus;
import com.vladputnikov.messaging_service.persistent.util.MessageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "recipient", nullable = false)
    private String recipient;

    @ElementCollection
    @CollectionTable(name = "ccs", joinColumns = @JoinColumn(name = "message_id"))
    private List<CarbonCopy> cc;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "text")
    private String text;

    @Column(name = "sent_at")
    private LocalDateTime messageSentAt;

    @Column(name = "delivered_at")
    private LocalDateTime messageDeliveredAt;

    @Column(name = "failed_at")
    private LocalDateTime messageFailedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MessageStatus messageStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MessageType messageType;

}
