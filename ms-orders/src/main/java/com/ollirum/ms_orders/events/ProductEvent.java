package com.ollirum.ms_orders.events;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductEvent implements Serializable {
    private String eventType;
    private Long id;
    private String name;
    private BigDecimal price;
    private Boolean isActive;
    private Integer stock;

    public ProductEvent() {
    }

    public ProductEvent(String eventType, Long id, String name, BigDecimal price, Boolean isActive, Integer stock) {
        this.eventType = eventType;
        this.id = id;
        this.name = name;
        this.price = price;
        this.isActive = isActive;
        this.stock = stock;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}