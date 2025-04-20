package com.ollirum.ms_profiles.services.impl;

import com.ollirum.ms_profiles.configuration.JwtTokenProvider;
import com.ollirum.ms_profiles.entities.Profile;
import com.ollirum.ms_profiles.exceptions.ProfileNotFoundException;
import com.ollirum.ms_profiles.exceptions.UnauthorizedAccessException;
import com.ollirum.ms_profiles.repositories.ProfileRepository;
import com.ollirum.ms_profiles.services.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public ProfileServiceImpl(ProfileRepository profileRepository, JwtTokenProvider jwtTokenProvider) {
        this.profileRepository = profileRepository;
        this.jwtTokenProvider = jwtTokenProvider;
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

    @Override
    public Profile getProfileById(Long id, String token) {
        Long authenticatedUserId = jwtTokenProvider.getUserIdFromToken(token);
        if (!authenticatedUserId.equals(id)) {
            throw new UnauthorizedAccessException("Você não tem permissão para acessar este perfil");
        }

        return profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Usuário(a) não encontrado(a)!"));
    }

    @Override
    public Profile getProfileByEmail(String email, String token) {
        String tokenEmail = jwtTokenProvider.getEmailFromToken(token);
        List<String> roles = jwtTokenProvider.getRolesFromToken(token);
        if (!email.equals(tokenEmail) && !roles.contains("ADMIN")) {
            throw new UnauthorizedAccessException("Você não tem permissão para acessar este perfil.");
        }

        return profileRepository.findByEmail(email)
                .orElseThrow(() -> new ProfileNotFoundException("Perfil não encontrado para o e-mail: " + email));
    }
}