package com.ollirum.ms_profiles.services.impl;

import com.ollirum.ms_profiles.entities.Profile;
import com.ollirum.ms_profiles.repositories.ProfileRepository;
import com.ollirum.ms_profiles.services.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional
    public void createProfile(Long userId, String name, String email, Set<String> roles) {
        if (profileRepository.findByEmail(email).isPresent()) {
            System.out.println("Perfil já existe para o e-mail: " + email);
            return;
        }

        Profile profile = new Profile();
        profile.setId(userId);
        profile.setEmail(email);
        profile.setName(name);

        profileRepository.save(profile);
    }
}