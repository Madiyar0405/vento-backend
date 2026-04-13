package dev.madiyar.vento.service;


import dev.madiyar.vento.entity.PasswordResetToken;
import dev.madiyar.vento.entity.User;
import dev.madiyar.vento.repository.PasswordResetTokenRepository;
import dev.madiyar.vento.repository.UserRepository;
import dev.madiyar.vento.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class PasswordResetService {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;



    @Autowired
    public PasswordResetService(UserRepository userRepository,
                                PasswordResetTokenRepository passwordResetTokenRepository,
                                PasswordEncoder passwordEncoder,
                                JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }



    @Transactional
    public String createResetToken(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        passwordResetTokenRepository.deleteByUser(user);
        String token = jwtService.generateToken(email);

        PasswordResetToken passwordResetToken = new PasswordResetToken();

        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(OffsetDateTime.now().plusMinutes(15));
        passwordResetTokenRepository.save(passwordResetToken);
        return token;
    }

    public PasswordResetToken validateToken(String token){
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Invalid token"));

        if(passwordResetToken.getExpiryDate().isBefore(OffsetDateTime.now())){
            throw new RuntimeException("token expired");
        }

        return passwordResetToken;
    }

    @Transactional
    public void resetPassword(String token, String newPassword){
        PasswordResetToken passwordResetToken = validateToken(token);

        User user = passwordResetToken.getUser();

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(passwordResetToken) ;

    }
}
