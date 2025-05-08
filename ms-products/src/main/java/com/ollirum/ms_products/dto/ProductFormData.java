package com.ollirum.ms_products.dto;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class ProductFormData {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Boolean isActive;
    private MultipartFile image;

    public ProductFormData() {
    }

    public ProductFormData(String name, String description, BigDecimal price, Integer stock, Boolean isActive, MultipartFile image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.isActive = isActive;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}