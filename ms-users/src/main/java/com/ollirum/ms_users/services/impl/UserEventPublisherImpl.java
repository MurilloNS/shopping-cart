package com.ollirum.ms_users.services.impl;

import com.ollirum.ms_users.constants.RabbitMQConstants;
import com.ollirum.ms_users.dto.UserCreatedEvent;
import com.ollirum.ms_users.services.UserEventPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventPublisherImpl implements UserEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public UserEventPublisherImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishUserCreated(UserCreatedEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConstants.USER_CREATED_QUEUE, event);
    }
}