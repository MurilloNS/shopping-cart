package com.ollirum.ms_profiles.dto;

public class AddressResponseDto {
    private Long id;
    private String road;
    private String number;
    private String city;
    private String state;

    public AddressResponseDto() {
    }

    public AddressResponseDto(Long id, String road, String number, String city, String state) {
        this.id = id;
        this.road = road;
        this.number = number;
        this.city = city;
        this.state = state;
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
}