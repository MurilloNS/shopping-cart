package com.ollirum.ms_users.services;

import com.ollirum.ms_users.entities.User;

public interface PasswordResetService {
    void verifyEmail(String email);
    String generatePasswordResetToken(User user);
    void sendEmail(String email, String token);
    void resetPassword(String token, String newPassword);
    void sendPasswordResetEmail(String to, String token);
}