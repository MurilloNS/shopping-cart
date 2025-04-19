package com.ollirum.ms_profiles.repositories;

import com.ollirum.ms_profiles.entities.Order;
import com.ollirum.ms_profiles.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByProfileId(Long profileId);
}