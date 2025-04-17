package com.ollirum.ms_users.dto;

import com.ollirum.ms_users.entities.Role;

import java.util.Set;

public class UserResponseDto {
    private String email;
    private Set<Role> roles;

    public UserResponseDto() {}

    public UserResponseDto(String email, Set<Role> roles) {
        this.email = email;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}