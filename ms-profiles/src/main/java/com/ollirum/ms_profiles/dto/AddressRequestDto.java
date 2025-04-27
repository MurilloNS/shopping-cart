package com.ollirum.ms_profiles.dto;

public class AddressRequestDto {
    private String road;
    private String number;
    private String city;
    private String state;
    private Long profileId;

    public AddressRequestDto() {
    }

    public AddressRequestDto(String road, String number, String city, String state, Long profileId) {
        this.road = road;
        this.number = number;
        this.city = city;
        this.state = state;
        this.profileId = profileId;
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

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
}