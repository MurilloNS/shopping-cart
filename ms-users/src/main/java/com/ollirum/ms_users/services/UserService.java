package com.ollirum.ms_users.services;

import com.ollirum.ms_users.dto.LoginRequestDto;
import com.ollirum.ms_users.dto.LoginResponseDto;
import com.ollirum.ms_users.dto.UserResponseDto;
import com.ollirum.ms_users.entities.User;

public interface UserService {
    UserResponseDto registerUser(User user);
    LoginResponseDto loginUser(LoginRequestDto loginRequestDto);
}