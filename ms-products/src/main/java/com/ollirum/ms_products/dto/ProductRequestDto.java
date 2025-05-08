package com.ollirum.ms_products.dto;

import java.math.BigDecimal;

public class ProductRequestDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Boolean isActive = true;

    public ProductRequestDto() {
    }

    public ProductRequestDto(String name, String description, BigDecimal price, Integer stock, Boolean isActive) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.isActive = isActive;
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
}