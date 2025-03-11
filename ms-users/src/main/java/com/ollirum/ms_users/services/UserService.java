package com.ollirum.ms_users.services;

import com.ollirum.ms_users.dto.UserResponseDto;
import com.ollirum.ms_users.entities.User;

public interface UserService {
    UserResponseDto registerUser(User user);
}