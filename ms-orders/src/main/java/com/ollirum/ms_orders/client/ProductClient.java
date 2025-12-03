package com.ollirum.ms_orders.client;

import com.ollirum.ms_orders.dto.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ms-products", url = "${ms-products.url}")
public interface ProductClient {
    @GetMapping("/api/products/{id}")
    ProductResponseDto getProductById(@PathVariable Long id, @RequestHeader("Authorization") String token);
}