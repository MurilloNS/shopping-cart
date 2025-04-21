package com.ollirum.ms_profiles.services.impl;

import com.ollirum.ms_profiles.client.UsersClient;
import com.ollirum.ms_profiles.configuration.JwtTokenProvider;
import com.ollirum.ms_profiles.entities.Profile;
import com.ollirum.ms_profiles.exceptions.ProfileNotFoundException;
import com.ollirum.ms_profiles.exceptions.UnauthorizedAccessException;
import com.ollirum.ms_profiles.repositories.ProfileRepository;
import com.ollirum.ms_profiles.services.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersClient usersClient;

    public ProfileServiceImpl(ProfileRepository profileRepository, JwtTokenProvider jwtTokenProvider, UsersClient usersClient) {
        this.profileRepository = profileRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usersClient = usersClient;
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

    @Override
    public Profile updateProfile(Long id, Map<String, Object> updates, String token) {
        Profile existingProfile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Perfil não encontrado."));

        String tokenEmail = jwtTokenProvider.getEmailFromToken(token);
        List<String> roles = jwtTokenProvider.getRolesFromToken(token);
        boolean isAdmin = roles.contains("ADMIN");
        boolean isOwner = existingProfile.getEmail().equals(tokenEmail);
        if (!isOwner && !isAdmin) {
            throw new UnauthorizedAccessException("Você não tem permissão para atualizar este perfil.");
        }

        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> existingProfile.setName((String) value);
            }
        });

        return profileRepository.save(existingProfile);
    }

    @Override
    public void disableProfile(Long id, String token) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Perfil não encontrado."));

        Long tokenUserId = jwtTokenProvider.getUserIdFromToken(token);
        List<String> roles = jwtTokenProvider.getRolesFromToken(token);
        if (!tokenUserId.equals(profile.getId()) && !roles.contains("ADMIN")) {
            throw new UnauthorizedAccessException("Você não ter permissão para desativar este perfil.");
        }

        profile.setEnabled(false);
        profileRepository.save(profile);
        usersClient.disableUser(profile.getId(), "Bearer " + token);
    }

    @Override
    public void deleteProfile(Long id, String token) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Perfil não encontrado."));

        usersClient.deleteUser(id, token);
        profileRepository.delete(profile);
    }

    @Override
    public List<Profile> findAll(String token) {
        List<String> roles = jwtTokenProvider.getRolesFromToken(token);
        if (!roles.contains("ADMIN")) {
            throw new UnauthorizedAccessException("Apenas administradores podem visualizar todos os perfis.");
        }

        return profileRepository.findAll();
    }
}