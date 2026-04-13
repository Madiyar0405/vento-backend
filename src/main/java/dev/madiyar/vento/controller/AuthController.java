package dev.madiyar.vento.controller;


import dev.madiyar.vento.dto.*;
import dev.madiyar.vento.service.AuthService;
import dev.madiyar.vento.service.EmailService;
import dev.madiyar.vento.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/api/v1/")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;
    private final PasswordResetService passwordResetService;

    @Autowired
    public AuthController(AuthService authService, PasswordResetService passwordResetService,EmailService emailService) {
        this.authService = authService;
        this.emailService = emailService;
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
        System.out.println("Email received: " + email); // посмотри что тут

        String token = passwordResetService.createResetToken(email);

        String link  =  "http://localhost:8081/reset-password?token=" + token;
        emailService.sendEmail(email, "Reset Password", link);
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
}
