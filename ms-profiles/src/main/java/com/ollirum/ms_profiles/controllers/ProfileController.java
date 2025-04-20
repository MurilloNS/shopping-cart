package com.ollirum.ms_profiles.controllers;

import com.ollirum.ms_profiles.configuration.JwtTokenProvider;
import com.ollirum.ms_profiles.entities.Profile;
import com.ollirum.ms_profiles.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    private final ProfileService profileService;
    private final JwtTokenProvider jwtTokenProvider;

    public ProfileController(ProfileService profileService, JwtTokenProvider jwtTokenProvider) {
        this.profileService = profileService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id,
                                                  @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        Profile profile = profileService.getProfileById(id, token);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/by-email")
    public ResponseEntity<Profile> getProfileByEmail(@RequestParam String email,
                                                     @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Profile profile = profileService.getProfileByEmail(email, token);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/me")
    public ResponseEntity<Profile> getProfileFromToken(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtTokenProvider.getEmailFromToken(token);
        Profile profile = profileService.getProfileByEmail(email, token);
        return ResponseEntity.ok(profile);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long id,
                                                 @RequestBody Map<String, Object> updates,
                                                 @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Profile updatedProfile = profileService.updateProfile(id, updates, token);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id,
                                              @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        profileService.disableProfile(id, token);
        return ResponseEntity.noContent().build();
    }
}