package com.ollirum.ms_users.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ollirum.ms_users.configuration.JwtTokenProvider;
import com.ollirum.ms_users.dto.UserResponseDto;
import com.ollirum.ms_users.entities.Role;
import com.ollirum.ms_users.entities.User;
import com.ollirum.ms_users.exceptions.EmailAlreadyExistsException;
import com.ollirum.ms_users.exceptions.NotFoundException;
import com.ollirum.ms_users.repositories.UserRepository;
import com.ollirum.ms_users.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRoles(new HashSet<>());

        Role role = new Role();
        role.setName("ROLE_USER");

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setName("Test User");
        responseDto.setEmail("test@example.com");
        responseDto.setRoles(Set.of(role));

        when(userService.registerUser(any(User.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"));
    }

    @Test
    void shouldFailWhenEmailAlreadyExists() throws Exception {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRoles(new HashSet<>());

        when(userService.registerUser(any(User.class)))
                .thenThrow(new EmailAlreadyExistsException("E-mail já registrado!"));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors").value("E-mail já registrado!"));
    }

    @Test
    void shouldFailWhenFieldsAreInvalid() throws Exception {
        User user = new User();
        user.setRoles(new HashSet<>());

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("O campo Nome é obrigatório."))
                .andExpect(jsonPath("$.email").value("O campo E-mail é obrigatório."))
                .andExpect(jsonPath("$.password").value("O campo Senha é obrigatório."));
    }

    @Test
    void shouldFailWhenRoleDoesNotExist() throws Exception {
        User user = new User();
        user.setName("User Test");
        user.setEmail("test@example.com");
        user.setPassword("123456");

        Role invalidRole = new Role();
        invalidRole.setName("ROLE_INEXISTENT");
        user.setRoles(Set.of(invalidRole));

        when(userService.registerUser(any(User.class)))
                .thenThrow(new NotFoundException("Role não encontrada: ROLE_INEXISTENT"));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors").value("Role não encontrada: ROLE_INEXISTENT"));
    }
}