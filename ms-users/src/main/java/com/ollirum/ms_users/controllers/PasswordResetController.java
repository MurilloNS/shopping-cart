package com.ollirum.ms_users.controllers;

import com.ollirum.ms_users.controllers.openapi.PasswordResetControllerOpenApi;
import com.ollirum.ms_users.dto.PasswordResetRequestDto;
import com.ollirum.ms_users.dto.ResetPasswordDto;
import com.ollirum.ms_users.services.PasswordResetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController implements PasswordResetControllerOpenApi {
    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/request")
    public ResponseEntity<String> requestPasswordReset(@RequestBody @Valid PasswordResetRequestDto requestDto) {
        passwordResetService.verifyEmail(requestDto.getEmail());
        return ResponseEntity.ok("E-mail de recuperação enviado com sucesso!");
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmPasswordReset(@RequestBody @Valid ResetPasswordDto resetPasswordDto) {
        passwordResetService.resetPassword(resetPasswordDto.getToken(), resetPasswordDto.getNewPassword());
        return ResponseEntity.ok("Senha redefinida com sucesso!");
    }
}