package com.ollirum.ms_users.services.impl;

import com.ollirum.ms_users.configuration.JwtTokenProvider;
import com.ollirum.ms_users.entities.User;
import com.ollirum.ms_users.repositories.UserRepository;
import com.ollirum.ms_users.services.PasswordResetService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetServiceImpl(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, JavaMailSender mailSender, PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${spring.security.jwt.password-reset-expiration}")
    private long resetTokenExpiration;

    @Override
    public void verifyEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("E-mail não encontrado!"));

        String token = generatePasswordResetToken(user);
        sendEmail(user.getEmail(), token);
    }

    @Override
    public String generatePasswordResetToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + resetTokenExpiration))
                .signWith(jwtTokenProvider.key(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public void sendEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Solicitação de Recuperação de Senha");
        message.setText("Clique no link para redefinir sua senha: http://localhost:8081/api/password-reset/confirm?token=" + token);
        mailSender.send(message);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("Token inválido ou expirado");
        }

        String email = jwtTokenProvider.getEmailFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void sendPasswordResetEmail(String to, String token) {
        String resetUrl = "http://localhost:8081/api/user/reset-password?token=" + token;
        String subject = "Recuperação de Senha";
        String message = "Olá,\n\nClique no link abaixo para redefinir sua senha:\n\n" + resetUrl;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom("murillo.murillo2001@gmail.com");
        mailSender.send(mailMessage);
    }
}