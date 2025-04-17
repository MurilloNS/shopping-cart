package com.ollirum.ms_users.services.impl;

import com.ollirum.ms_users.configuration.JwtTokenProvider;
import com.ollirum.ms_users.dto.LoginRequestDto;
import com.ollirum.ms_users.dto.LoginResponseDto;
import com.ollirum.ms_users.dto.UserResponseDto;
import com.ollirum.ms_users.entities.Role;
import com.ollirum.ms_users.entities.User;
import com.ollirum.ms_users.exceptions.EmailAlreadyExistsException;
import com.ollirum.ms_users.exceptions.InvalidCredentialsException;
import com.ollirum.ms_users.repositories.RoleRepository;
import com.ollirum.ms_users.repositories.UserRepository;
import com.ollirum.ms_users.services.UserService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public UserResponseDto registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("E-mail já registrado!");
        }

        Set<Role> roles = user.getRoles();
        if (roles == null || roles.isEmpty()) {
            Role defaultRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new NotFoundException("Role padrão não encontrada!"));
            roles = Collections.singleton(defaultRole);
        } else {
            roles = roles.stream().map(role -> roleRepository.findByName(role.getName()).orElseThrow(() -> new NotFoundException("Role não encontrada: " + role.getName()))).collect(Collectors.toSet());
        }

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail(savedUser.getEmail());
        userResponseDto.setRoles(savedUser.getRoles());

        return userResponseDto;
    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(userDetails);
            return new LoginResponseDto(token);
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("E-mail ou senha inválidos!");
        }
    }
}