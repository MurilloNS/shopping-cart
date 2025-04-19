package com.ollirum.ms_users.dto;

import java.util.Set;

public class UserCreatedEvent {
    private Long userId;
    private String name;
    private String email;
    private Set<String> roles;

    public UserCreatedEvent() {
    }

    public UserCreatedEvent(Long userId, String name, String email, Set<String> roles) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}