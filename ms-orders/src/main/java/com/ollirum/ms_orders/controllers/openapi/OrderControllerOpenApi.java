package com.ollirum.ms_orders.controllers.openapi;

import com.ollirum.ms_orders.entities.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Orders", description = "Gerenciamento de pedidos")
public interface OrderControllerOpenApi {
    @Operation(
            summary = "Cria um novo pedido",
            description = "Cria um novo pedido",
            responses = {
                    @ApiResponse(responseCode = "201",description = "Pedido criado com sucesso",
                            content = @Content(schema = @Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    ResponseEntity<Order>createOrder(@RequestBody Order order, @Parameter(hidden = true) String authorizationHeader);

    @Operation(
            summary = "Cancela um pedido",
            description = "Altera o status de um pedido específico para CANCELADO",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido cancelado com sucesso",
                            content = @Content(schema = @Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
            }
    )
    ResponseEntity<Order> cancelOrder(@PathVariable Long id);

    @Operation(
            summary = "Busca pedido por ID",
            description = "Retorna um pedido específico pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido encontrado",
                            content = @Content(schema = @Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
            }
    )
    ResponseEntity<Order> getOrderById(@PathVariable Long id);

    @Operation(
            summary = "Lista pedidos por ID do usuário",
            description = "Retorna todos os pedidos vinculados a um determinado perfil",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                            content = @Content(schema = @Schema(implementation = Order[].class))),
                    @ApiResponse(responseCode = "404", description = "Nenhum pedido encontrado")
            }
    )
    ResponseEntity<List<Order>> getAllByUserId(@PathVariable Long profileId);
}