package com.ollirum.ms_profiles.services;

import com.ollirum.ms_profiles.entities.Profile;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProfileService {
    void createProfile(Long userId, String email, String name, Set<String> roles);
    Profile getProfileById(Long id, String token);
    Profile getProfileByEmail(String email, String token);
    Profile updateProfile(Long id, Map<String, Object> updates, String token);
    void disableProfile(Long id, String token);
    void deleteProfile(Long id, String token);
    List<Profile> findAll(String token);
}