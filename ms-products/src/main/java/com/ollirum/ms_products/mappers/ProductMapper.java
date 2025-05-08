package com.ollirum.ms_products.mappers;

import com.ollirum.ms_products.dto.ProductRequestDto;
import com.ollirum.ms_products.dto.ProductResponseDto;
import com.ollirum.ms_products.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toEntity(ProductRequestDto dto, Long profileId) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setActive(true);
        product.setProfileId(profileId);
        return product;
    }

    public ProductResponseDto toDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setImageUrl(product.getImageUrl());
        dto.setActive(product.getActive());
        dto.setCreatedAt(product.getCreatedAt());
        return dto;
    }
}