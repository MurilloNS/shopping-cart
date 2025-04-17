package com.ollirum.ms_profiles.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "profile")
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<Order> ordersHistory = new ArrayList<>();

    public Profile() {
    }

    public Profile(Long id, String nome, String email, List<Address> addresses, List<Order> ordersHistory) {
        this.id = id;
        this.nome = nome;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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