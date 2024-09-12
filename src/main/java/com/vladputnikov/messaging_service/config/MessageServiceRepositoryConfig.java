package com.vladputnikov.messaging_service.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.vladputnikov.messaging_service")
@EnableJpaRepositories("com.vladputnikov.messaging_service.persistent.repository")
public class MessageServiceRepositoryConfig {
}
