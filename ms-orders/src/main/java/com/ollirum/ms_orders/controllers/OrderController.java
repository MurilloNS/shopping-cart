package com.ollirum.ms_orders.controllers;

import com.ollirum.ms_orders.entities.Order;
import com.ollirum.ms_orders.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestBody Order order,
            @RequestHeader("Authorization") String authorizationHeader) {
        Order savedOrder = orderService.createOrder(order, authorizationHeader);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }


    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long id) {
        Order cancelledOrder = orderService.cancelOrder(id);
        return ResponseEntity.ok(cancelledOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{profileId}/list")
    public ResponseEntity<List<Order>> getAllByUserId(@PathVariable Long profileId) {
        List<Order> orders = orderService.getAllByProfileId(profileId);
        return ResponseEntity.ok(orders);
    }
}