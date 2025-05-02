package com.ollirum.ms_orders.services;

import com.ollirum.ms_orders.entities.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order, String authorizationHeader);
    Order cancelOrder(Long orderId);
    Order getOrderById(Long id);
    List<Order> getAllByProfileId(Long profileId);
}