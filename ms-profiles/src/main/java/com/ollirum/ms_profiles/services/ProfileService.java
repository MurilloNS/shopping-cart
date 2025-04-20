package com.ollirum.ms_profiles.services;

import com.ollirum.ms_profiles.entities.Profile;

import java.util.Optional;
import java.util.Set;

public interface ProfileService {
    void createProfile(Long userId, String email, String name, Set<String> roles);
    Profile getProfileById(Long id, String token);
    Profile getProfileByEmail(String email, String token);
}