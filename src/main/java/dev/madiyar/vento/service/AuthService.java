package dev.madiyar.vento.service;


import dev.madiyar.vento.dto.JwtAuthDto;
import dev.madiyar.vento.dto.RegisterRequest;
import dev.madiyar.vento.dto.RegisterResponse;
import dev.madiyar.vento.entity.User;
import dev.madiyar.vento.repository.UserRepository;
import dev.madiyar.vento.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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
