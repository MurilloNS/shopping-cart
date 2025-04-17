package com.ollirum.ms_profiles.entities;

import jakarta.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String road;

    @Column(length = 10)
    private String number;

    @Column(length = 50)
    private String city;

    @Column(length = 2)
    private String state;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public Address() {
    }

    public Address(Long id, String road, String number, String city, String state, Profile profile) {
        this.id = id;
        this.road = road;
        this.number = number;
        this.city = city;
        this.state = state;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}