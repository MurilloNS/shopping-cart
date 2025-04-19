package com.ollirum.ms_users.services;

import com.ollirum.ms_users.dto.UserCreatedEvent;

public interface UserEventPublisher {
    void publishUserCreated(UserCreatedEvent event);
}