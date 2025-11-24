package com.ollirum.ms_profiles.controllers.openapi;

import com.ollirum.ms_profiles.dto.AddressRequestDto;
import com.ollirum.ms_profiles.dto.AddressResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Tag(name = "Address", description = "Gerenciamento de endereços associados a perfis de usuário")
public interface AddressControllerOpenApi {
    @Operation(
            summary = "Criar um endereço",
            description = "Cria um novo endereço associado a um perfil específico",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Endereço criado com sucesso",
                            content = @Content(schema = @Schema(implementation = AddressResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado")
            }
    )
    ResponseEntity<AddressResponseDto> createAddress(@RequestBody AddressRequestDto addressRequestDto);

    @Operation(
            summary = "Listar endereços de um perfil",
            description = "Retorna todos os endereços associados ao perfil informado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de endereços retornada com sucesso",
                            content = @Content(schema = @Schema(implementation = AddressResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Perfil não encontrado"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado")
            }
    )
    ResponseEntity<List<AddressResponseDto>> getAddressesByProfile(@PathVariable Long profileId);

    @Operation(
            summary = "Atualizar informações de um endereço",
            description = "Atualiza os campos enviados no corpo da requisição",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Endereço atualizado com sucesso",
                            content = @Content(schema = @Schema(implementation = AddressResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado")
            }
    )
    ResponseEntity<AddressResponseDto> updateAddress(@PathVariable Long id, @RequestBody Map<String, Object> updates);


    @Operation(
            summary = "Excluir um endereço",
            description = "Remove um endereço pelo seu ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Endereço excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado")
            }
    )
    ResponseEntity<Void> deleteAddress(@PathVariable Long id);
}