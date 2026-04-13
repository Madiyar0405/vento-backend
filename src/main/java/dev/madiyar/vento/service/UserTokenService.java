package dev.madiyar.vento.service;


import dev.madiyar.vento.entity.UserToken;
import dev.madiyar.vento.entity.User;
import dev.madiyar.vento.enums.TokenType;
import dev.madiyar.vento.repository.UserTokenRepository;
import dev.madiyar.vento.repository.UserRepository;
import dev.madiyar.vento.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class UserTokenService {
    private final UserRepository userRepository;
    private final UserTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;


    @Autowired
    public UserTokenService(UserRepository userRepository,
                            UserTokenRepository passwordResetTokenRepository,
                            PasswordEncoder passwordEncoder, JwtService jwtService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    public void initForgotPassword(String email){
        String token = createToken(email, TokenType.PASSWORD_RESET);
        String link  =  "http://localhost:8081/reset-password?token=" + token;
        emailService.sendEmail(email, "Reset Password", link);
    }

    @Transactional
    public String createToken(String email, TokenType type){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        passwordResetTokenRepository.deleteByUserAndType(user, type);
        String token = jwtService.generateToken(email);

        UserToken passwordResetToken = new UserToken();

        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(OffsetDateTime.now().plusMinutes(15));
        passwordResetToken.setType(type);
        passwordResetTokenRepository.save(passwordResetToken);
        return token;
    }

    public UserToken validateToken(String token){
        UserToken passwordResetToken = passwordResetTokenRepository.findByTokenAndType(token, TokenType.PASSWORD_RESET)
                .orElseThrow(() -> new EntityNotFoundException("Invalid token"));

        if(passwordResetToken.getExpiryDate().isBefore(OffsetDateTime.now())){
            throw new RuntimeException("token expired");
        }

        return passwordResetToken;
    }

    @Transactional
    public void resetPassword(String token, String newPassword){
        UserToken passwordResetToken = validateToken(token);

        User user = passwordResetToken.getUser();

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(passwordResetToken) ;

    }


    @Transactional
    public void verifyEmail(String token){
        UserToken verifyToken = passwordResetTokenRepository.findByTokenAndType(token, TokenType.EMAIL_VERIFICATION)
                .orElseThrow(() -> new EntityNotFoundException("Invalid token"));


        if(verifyToken.getExpiryDate().isBefore(OffsetDateTime.now())){
            throw new RuntimeException("token expired");
        }

        User user = verifyToken.getUser();
        user.setEmailVerified(true);
        userRepository.save(user);
        passwordResetTokenRepository.deleteByUserAndType(user, TokenType.EMAIL_VERIFICATION);

    }
}
