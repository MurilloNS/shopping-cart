package com.ollirum.ms_products.controllers;

import com.ollirum.ms_products.controllers.openapi.ProductControllerOpenApi;
import com.ollirum.ms_products.dto.ProductFormData;
import com.ollirum.ms_products.dto.ProductRequestDto;
import com.ollirum.ms_products.dto.ProductResponseDto;
import com.ollirum.ms_products.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
public class ProductController implements ProductControllerOpenApi {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDto> createProduct(
            @ModelAttribute ProductFormData form,
            @RequestHeader("Authorization") String token
    ) {
        ProductRequestDto dto = new ProductRequestDto(
                form.getName(), form.getDescription(), form.getPrice(),
                form.getStock(), form.getActive()
        );

        ProductResponseDto response = productService.createProduct(dto, form.getImage(), token);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        ProductResponseDto dto = productService.getProductById(id, token);
        return ResponseEntity.ok(dto);
    }
}