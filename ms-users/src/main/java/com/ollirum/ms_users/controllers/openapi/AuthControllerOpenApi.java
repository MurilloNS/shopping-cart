package com.ollirum.ms_users.controllers.openapi;

import com.ollirum.ms_users.dto.LoginRequestDto;
import com.ollirum.ms_users.dto.LoginResponseDto;
import com.ollirum.ms_users.dto.UserResponseDto;
import com.ollirum.ms_users.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "Endpoints de autenticação e gerenciamento de usuários")
public interface AuthControllerOpenApi {

    @Operation(
            summary = "Registrar novo usuário",
            description = "Cria um novo usuário no sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuário registrado com sucesso",
                            content = @Content(
                                    schema = @Schema(implementation = UserResponseDto.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "id": 10,
                                                "name": "Murillo",
                                                "email": "murillo@email.com",
                                                "enabled": true
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados inválidos",
                            content = @Content(
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "error": "Email já está cadastrado"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody User dto);


    @Operation(
            summary = "Login",
            description = "Autentica o usuário e retorna um token JWT",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login realizado com sucesso",
                            content = @Content(
                                    schema = @Schema(implementation = LoginResponseDto.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "token": "eyJh...abc"
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credenciais inválidas",
                            content = @Content(
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "error": "Email ou senha incorretos"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto);


    @Operation(
            summary = "Desativar usuário",
            description = "Desativa um usuário já cadastrado",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário desativado com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
                            content = @Content(
                                    examples = @ExampleObject(value = "{ \"error\": \"Usuário não encontrado\" }")
                            )
                    )
            }
    )
    ResponseEntity<Void> disableUser(@PathVariable Long id);


    @Operation(
            summary = "Excluir usuário",
            description = "Remove um usuário permanentemente",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
                            content = @Content(
                                    examples = @ExampleObject(value = "{ \"error\": \"Usuário não encontrado\" }")
                            )
                    )
            }
    )
    ResponseEntity<Void> deleteUser(@PathVariable Long id);
}