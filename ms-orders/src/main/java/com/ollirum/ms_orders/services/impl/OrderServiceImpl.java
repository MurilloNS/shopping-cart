package com.ollirum.ms_orders.services.impl;

import com.ollirum.ms_orders.configuration.JwtTokenProvider;
import com.ollirum.ms_orders.entities.Order;
import com.ollirum.ms_orders.enums.OrderStatus;
import com.ollirum.ms_orders.exceptions.OrderCancellationException;
import com.ollirum.ms_orders.exceptions.OrderNotFoundException;
import com.ollirum.ms_orders.exceptions.UnauthorizedAccessException;
import com.ollirum.ms_orders.repositories.OrderRepository;
import com.ollirum.ms_orders.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpServletRequest request;

    public OrderServiceImpl(OrderRepository orderRepository, JwtTokenProvider jwtTokenProvider, HttpServletRequest request) {
        this.orderRepository = orderRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.request = request;
    }

    @Override
    @Transactional
    public Order createOrder(Order order, String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new UnauthorizedAccessException("Token JWT ausente ou inválido.");
        }

        String token = authorizationHeader.replace("Bearer ", "");
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        order.setProfileId(userId);
        return orderRepository.save(order);
    }

    @Override
    public Order cancelOrder(Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedAccessException("Usuário(a) não autenticado(a).");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado"));

        if (order.getStatus() == OrderStatus.CANCELLED) throw new OrderCancellationException("O pedido já foi cancelado");
        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedAccessException("Usuário(a) não autenticado(a).");
        }
        
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado"));
    }

    @Override
    public List<Order> getAllByProfileId(Long profileId) {
        List<Order> orders = orderRepository.getAllByProfileId(profileId);
        System.out.println("Total de pedidos retornados: " + orders.size());

        return orders;
    }

}