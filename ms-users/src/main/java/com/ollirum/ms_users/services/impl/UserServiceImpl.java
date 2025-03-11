package com.ollirum.ms_users.services.impl;

import com.ollirum.ms_users.dto.UserResponseDto;
import com.ollirum.ms_users.entities.Role;
import com.ollirum.ms_users.entities.User;
import com.ollirum.ms_users.exceptions.EmailAlreadyExistsException;
import com.ollirum.ms_users.repositories.RoleRepository;
import com.ollirum.ms_users.repositories.UserRepository;
import com.ollirum.ms_users.services.UserService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserResponseDto registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email já registrado!");
        }

        Set<Role> roles = user.getRoles();
        if (roles == null || roles.isEmpty()) {
            Role defaultRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new NotFoundException("Role padrão não encontrada!"));
            roles = Collections.singleton(defaultRole);
        } else {
            roles = roles.stream().map(role -> roleRepository.findByName(role.getName()).orElseThrow(() -> new NotFoundException("Role não encontrada: " + role.getName()))).collect(Collectors.toSet());
        }

        user.setRoles(roles);
        user.setPassword(user.getPassword());
        User savedUser = userRepository.save(user);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setName(savedUser.getName());
        userResponseDto.setEmail(savedUser.getEmail());
        userResponseDto.setRoles(savedUser.getRoles());

        return userResponseDto;
    }
}