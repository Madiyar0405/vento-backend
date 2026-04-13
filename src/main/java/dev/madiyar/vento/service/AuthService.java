package dev.madiyar.vento.service;


import dev.madiyar.vento.dto.JwtAuthDto;
import dev.madiyar.vento.dto.RegisterRequest;
import dev.madiyar.vento.dto.RegisterResponse;
import dev.madiyar.vento.entity.User;
import dev.madiyar.vento.entity.UserToken;
import dev.madiyar.vento.enums.TokenType;
import dev.madiyar.vento.repository.UserRepository;
import dev.madiyar.vento.repository.UserTokenRepository;
import dev.madiyar.vento.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final UserTokenService userTokenService;


    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtService jwtService, EmailService emailService, UserTokenService userTokenService ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.userTokenService = userTokenService;
    }

    public JwtAuthDto register(RegisterRequest request){

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email уже занят");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        userRepository.save(user);

        String token = jwtService.generateToken(request.getEmail());
        String verifyToken = userTokenService.createToken(request.getEmail(), TokenType.EMAIL_VERIFICATION);
        String link  =  "http://localhost:8081/verify?token=" + token;
        emailService.sendEmail(request.getEmail(), "Verify token", verifyToken);

        return new JwtAuthDto(token);
    }

    public JwtAuthDto login(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Неверный пароль" + user.getPasswordHash() + " " + password);
        }

        String token = jwtService.generateToken(email);

        return new JwtAuthDto(token);
    }
}
