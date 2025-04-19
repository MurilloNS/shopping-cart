package com.ollirum.ms_profiles.services;

import java.util.Set;

public interface ProfileService {
    void createProfile(Long userId, String email, String name, Set<String> roles);
}