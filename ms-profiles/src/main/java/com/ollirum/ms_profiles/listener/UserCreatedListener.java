package com.ollirum.ms_profiles.listener;

import com.ollirum.ms_profiles.constants.RabbitMQConstants;
import com.ollirum.ms_profiles.dto.UserCreatedEvent;
import com.ollirum.ms_profiles.services.ProfileService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserCreatedListener {
    private final ProfileService profileService;

    public UserCreatedListener(ProfileService profileService) {
        this.profileService = profileService;
    }

    @RabbitListener(queues = RabbitMQConstants.USER_CREATED_QUEUE)
    public void handleUserCreated(UserCreatedEvent event, Channel channel, Message message) throws IOException {
        try {
            profileService.createProfile(event.getUserId(), event.getName(), event.getEmail(), event.getRoles());
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}