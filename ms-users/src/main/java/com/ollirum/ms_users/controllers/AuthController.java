package com.ollirum.ms_users.controllers;

import com.ollirum.ms_users.configuration.JwtTokenProvider;
import com.ollirum.ms_users.dto.LoginRequestDto;
import com.ollirum.ms_users.dto.LoginResponseDto;
import com.ollirum.ms_users.dto.UserResponseDto;
import com.ollirum.ms_users.entities.User;
import com.ollirum.ms_users.exceptions.NotFoundException;
import com.ollirum.ms_users.repositories.UserRepository;
import com.ollirum.ms_users.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService, UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody User user) {
        UserResponseDto userResponseDto = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = userService.loginUser(loginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDto);
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<Void> disableUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário(a) não encontrado(a)"));

        user.setEnabled(false);
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário(a) não encontrado(a)."));

        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }
}