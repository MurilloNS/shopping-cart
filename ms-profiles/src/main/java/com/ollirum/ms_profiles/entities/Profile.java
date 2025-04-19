package com.ollirum.ms_profiles.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profile")
public class Profile {
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "profile")
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<Order> ordersHistory = new ArrayList<>();

    public Profile() {
    }

    public Profile(Long id, String name, String email, List<Address> addresses, List<Order> ordersHistory) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.addresses = addresses;
        this.ordersHistory = ordersHistory;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Order> getOrdersHistory() {
        return ordersHistory;
    }

    public void setOrdersHistory(List<Order> ordersHistory) {
        this.ordersHistory = ordersHistory;
    }
}