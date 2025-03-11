package com.ollirum.ms_users.controllers;

import com.ollirum.ms_users.dto.UserResponseDto;
import com.ollirum.ms_users.entities.User;
import com.ollirum.ms_users.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody User user) {
        UserResponseDto userResponseDto = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }
}