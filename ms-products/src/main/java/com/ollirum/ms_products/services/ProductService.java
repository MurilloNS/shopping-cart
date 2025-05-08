package com.ollirum.ms_products.services;

import com.ollirum.ms_products.dto.ProductRequestDto;
import com.ollirum.ms_products.dto.ProductResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto dto, MultipartFile image, String token);
}