package com.vladputnikov.messaging_service.persistent.repository;

import com.vladputnikov.messaging_service.persistent.model.Message;
import com.vladputnikov.messaging_service.persistent.util.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m.messageStatus FROM Message m where m.id=:id")
    Optional<MessageStatus> getMessageStatusById(@Param("id") Long id);

    @Query("SELECT m FROM Message m where m.subject=:subject")
    Optional<List<Message>> getAllMessagesBySubject(@Param("subject") String subject);

}
