package com.ollirum.ms_products.services.impl;

import com.ollirum.ms_products.configuration.JwtTokenProvider;
import com.ollirum.ms_products.dto.ProductRequestDto;
import com.ollirum.ms_products.dto.ProductResponseDto;
import com.ollirum.ms_products.entities.Product;
import com.ollirum.ms_products.exceptions.ProductNotFoundException;
import com.ollirum.ms_products.exceptions.UnauthorizedAccessException;
import com.ollirum.ms_products.mappers.ProductMapper;
import com.ollirum.ms_products.repositories.ProductRepository;
import com.ollirum.ms_products.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, JwtTokenProvider jwtTokenProvider, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto dto, MultipartFile image, String token) {
        List<String> roles = jwtTokenProvider.getRolesFromToken(token);
        if (roles == null || !roles.contains("ADMIN")) {
            throw new UnauthorizedAccessException("Apenas administradores(as) podem cadastrar produtos.");
        }

        Long profileId = jwtTokenProvider.getUserIdFromToken(token);
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path uploadPath = Paths.get("D:/Spring/uploads");
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path fullPath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), fullPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem", e);
        }

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setProfileId(profileId);
        product.setActive(true);
        product.setImageUrl("/uploads/" + fileName);

        Product saved = productRepository.save(product);

        return productMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id, String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new UnauthorizedAccessException("Token inválido ou expirado");
        }

        List<String> roles = jwtTokenProvider.getRolesFromToken(token);
        if (roles == null || roles.isEmpty()) {
            throw new UnauthorizedAccessException("Acesso não autorizado");
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado!"));

        return productMapper.toDto(product);
    }
}