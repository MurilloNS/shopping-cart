package com.ollirum.ms_profiles.controllers.openapi;

import com.ollirum.ms_profiles.entities.Profile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Tag(name = "Profiles", description = "Endpoints para gerenciamento de perfis de usuários")
public interface ProfileControllerOpenApi {
    @Operation(
            summary = "Buscar perfil pelo ID",
            description = "Retorna um perfil específico com base no ID informado",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponse(
            responseCode = "200",
            description = "Perfil encontrado",
            content = @Content(schema = @Schema(implementation = Profile.class))
    )
    @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
    ResponseEntity<Profile> getProfileById(
            @Parameter(description = "ID do perfil") Long id,
            @Parameter(hidden = true) String authorizationHeader
    );

    @Operation(
            summary = "Buscar perfil por email",
            description = "Retorna um perfil baseado no email informado",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponse(
            responseCode = "200",
            description = "Perfil encontrado"
    )
    ResponseEntity<Profile> getProfileByEmail(
            @Parameter(description = "Email do usuário") String email,
            @Parameter(hidden = true) String authHeader
    );
    @Operation(
            summary = "Obter informações do próprio perfil",
            description = "Extrai o e-mail do token JWT e retorna o perfil do usuário autenticado.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    ResponseEntity<Profile> getProfileFromToken(
            @Parameter(hidden = true) String authHeader
    );

    @Operation(
            summary = "Atualizar dados do perfil",
            description = "Atualiza campos específicos de um perfil existente.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    ResponseEntity<Profile> updateProfile(
            @Parameter(description = "ID do perfil") Long id,
            @Parameter(description = "Dados a serem atualizados") Map<String, Object> updates,
            @Parameter(hidden = true) String authHeader
    );

    @Operation(
            summary = "Desativar perfil",
            description = "Desativa o perfil mas mantém no banco.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    ResponseEntity<Void> disableProfile(
            @Parameter(description = "ID do perfil") Long id,
            @Parameter(hidden = true) String authHeader
    );

    @Operation(
            summary = "Excluir perfil",
            description = "Deleta permanentemente o perfil.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    ResponseEntity<Void> deleteProfile(
            @Parameter(description = "ID do perfil") Long id,
            @Parameter(hidden = true) String token
    );

    @Operation(
            summary = "Listar todos os perfis",
            description = "Retorna todos os perfis cadastrados.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    ResponseEntity<List<Profile>> getAllProfiles(
            @Parameter(hidden = true) String token
    );
}