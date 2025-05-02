package com.ollirum.ms_orders.repositories;

import com.ollirum.ms_orders.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getAllByProfileId(Long profileId);
}