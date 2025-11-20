package com.ollirum.ms_users.controllers.openapi;

import com.ollirum.ms_users.dto.PasswordResetRequestDto;
import com.ollirum.ms_users.dto.ResetPasswordDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Password Reset", description = "Endpoints para solicitar e redefinir senha esquecida")
public interface PasswordResetControllerOpenApi {
    @Operation(
            summary = "Solicitar redefinição de senha",
            description = "Envia um e-mail contendo o token de redefinição de senha para o usuário.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "E-mail de recuperação enviado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "E-mail não encontrado no sistema"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida",
                            content = @Content(schema = @Schema(implementation = PasswordResetRequestDto.class))
                    )
            }
    )
    ResponseEntity<String> requestPasswordReset(@Valid @RequestBody PasswordResetRequestDto requestDto);


    @Operation(
            summary = "Confirmar redefinição de senha",
            description = "Valida o token enviado por e-mail e atualiza a senha do usuário.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Senha redefinida com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Token inválido ou expirado"
                    )
            }
    )
    ResponseEntity<String> confirmPasswordReset(@Valid @RequestBody ResetPasswordDto resetPasswordDto);
}