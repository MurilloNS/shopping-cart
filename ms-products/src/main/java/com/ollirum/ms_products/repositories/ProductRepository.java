package com.ollirum.ms_products.repositories;

import com.ollirum.ms_products.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}