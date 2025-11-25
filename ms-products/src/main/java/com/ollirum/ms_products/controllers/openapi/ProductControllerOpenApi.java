package com.ollirum.ms_products.controllers.openapi;

import com.ollirum.ms_products.dto.ProductFormData;
import com.ollirum.ms_products.dto.ProductResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "Product", description = "Gerenciamento de produtos")
public interface ProductControllerOpenApi {
    @Operation(
            summary = "Criar um novo produto",
            description = """
                    Endpoint para cadastrar um novo produto
                    Aceita dados em **multipart/form-data** contendo:
                    
                    - Informações do produto
                    - Imagem do produto
                    """,
            responses = {
                    @ApiResponse(responseCode = "201",
                    description = "Produto criado com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = ProductResponseDto.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado (token inválido ou ausente)"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    ResponseEntity<ProductResponseDto> createProduct(
            @ModelAttribute ProductFormData form,
            @Parameter(hidden = true) String token
    );
}