package com.ollirum.ms_users.services.impl;

import com.ollirum.ms_users.configuration.JwtTokenProvider;
import com.ollirum.ms_users.dto.UserCreatedEvent;
import com.ollirum.ms_users.dto.UserResponseDto;
import com.ollirum.ms_users.entities.Role;
import com.ollirum.ms_users.entities.User;
import com.ollirum.ms_users.exceptions.EmailAlreadyExistsException;
import com.ollirum.ms_users.repositories.RoleRepository;
import com.ollirum.ms_users.repositories.UserRepository;
import com.ollirum.ms_users.services.UserEventPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserEventPublisher userEventPublisher;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        User user = new User();
        user.setEmail("test@example.com");

        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        assertThrows(EmailAlreadyExistsException.class, () -> userService.registerUser(user));
    }

    @Test
    void shouldSetDefaultRoleWhenRolesAreNull() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("123456");
        user.setRoles(null);

        Role defaultRole = new Role();
        defaultRole.setName("ROLE_USER");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Test User");
        savedUser.setEmail(user.getEmail());
        savedUser.setPassword("encodedPassword");
        savedUser.setRoles(Collections.singleton(defaultRole));

        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(defaultRole));
        Mockito.when(passwordEncoder.encode("123456")).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(savedUser);

        UserResponseDto response = userService.registerUser(user);

        assertNotNull(response);
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(1, response.getRoles().size());
        assertEquals("ROLE_USER", response.getRoles().iterator().next().getName());

        Mockito.verify(userEventPublisher).publishUserCreated(Mockito.any(UserCreatedEvent.class));
    }

    @Test
    void shouldSetDefaultRoleWhenRolesIsEmpty() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setRoles(new HashSet<>());

        Role defaultRole = new Role();
        defaultRole.setName("ROLE_USER");

        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(defaultRole));
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Test User");
        savedUser.setEmail(user.getEmail());
        savedUser.setRoles(Set.of(defaultRole));

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(savedUser);

        UserResponseDto response = userService.registerUser(user);

        assertNotNull(response);
        assertEquals("ROLE_USER", response.getRoles().iterator().next().getName());
    }

    @Test
    void shouldSetGivenRolesWhenValid() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        Set<Role> roles = new HashSet<>();

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roles.add(adminRole);
        user.setRoles(roles);

        Role savedRole = new Role();
        savedRole.setName("ROLE_ADMIN");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("test@example.com");
        savedUser.setPassword("encodedPassword");
        savedUser.setRoles(roles);

        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        Mockito.when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(savedRole));
        Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(savedUser);

        UserResponseDto userResponseDto = userService.registerUser(user);

        assertNotNull(userResponseDto);
        assertEquals(1, userResponseDto.getRoles().size());
        assertEquals("ROLE_ADMIN", userResponseDto.getRoles().iterator().next().getName());
    }

    @Test
    void shouldThrowExceptionWhenRoleNotFound() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        Set<Role> roles = new HashSet<>();

        Role inexistentRole = new Role();
        inexistentRole.setName("ROLE_INEXISTENT");
        roles.add(inexistentRole);
        user.setRoles(roles);

        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        Mockito.when(roleRepository.findByName("ROLE_INEXISTENT")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.registerUser(user));
    }

    @Test
    void shouldEncodePasswordBeforeSavingUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("plainPassword");

        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        Role defaultRole = new Role();
        defaultRole.setName("ROLE_USER");

        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(defaultRole));
        Mockito.when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setEmail(user.getEmail());
        savedUser.setPassword("encodedPassword");
        savedUser.setName("Test User");
        savedUser.setRoles(Set.of(defaultRole));
        savedUser.setId(1L);

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(savedUser);

        UserResponseDto response = userService.registerUser(user);

        assertNotNull(response);
        Mockito.verify(passwordEncoder).encode("plainPassword");
        assertEquals("Test User", response.getName());
        assertEquals("test@example.com", response.getEmail());
    }

    @Test
    void shouldPublishEventWhenUserIsCreated() {
        User user = new User();
        user.setEmail("event@example.com");
        user.setPassword("password");
        user.setName("Event User");

        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        Role defaultRole = new Role();
        defaultRole.setName("ROLE_USER");

        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(defaultRole));
        Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail(user.getEmail());
        savedUser.setPassword("encodedPassword");
        savedUser.setName(user.getName());
        savedUser.setRoles(Set.of(defaultRole));

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(savedUser);

        userService.registerUser(user);

        Mockito.verify(userEventPublisher).publishUserCreated(Mockito.argThat(event ->
                event.getUserId().equals(1L) &&
                event.getEmail().equals("event@example.com") &&
                event.getName().equals("Event User") &&
                event.getRoles().contains("ROLE_USER")));
    }
}