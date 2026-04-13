package dev.madiyar.vento.controller;


import dev.madiyar.vento.dto.*;
import dev.madiyar.vento.enums.TokenType;
import dev.madiyar.vento.service.AuthService;
import dev.madiyar.vento.service.EmailService;
import dev.madiyar.vento.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/api/v1/")
public class AuthController {

    private final AuthService authService;
    private final UserTokenService passwordResetService;

    @Autowired
    public AuthController(AuthService authService, UserTokenService passwordResetService) {
        this.authService = authService;
        this.passwordResetService = passwordResetService;
    }


    @PostMapping("/login")
    public ResponseEntity<JwtAuthDto> login(@RequestBody UserCredentialsDto request) {
        return ResponseEntity.ok(authService.login(request.getEmail(), request.getPassword()));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthDto> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestParam String email
    ){
        passwordResetService.initForgotPassword(email);

        return ResponseEntity.ok("Reset link sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword
    ) {
        passwordResetService.resetPassword(token, newPassword);

        return ResponseEntity.ok("Password successfully updated");
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam String token){
        passwordResetService.verifyEmail(token);
        return ResponseEntity.ok("Email verified");
    }

}
