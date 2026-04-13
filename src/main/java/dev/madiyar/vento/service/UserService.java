package dev.madiyar.vento.service;


import dev.madiyar.vento.dto.UserResponseDto;
import dev.madiyar.vento.dto.mapper.UserMapper;
import dev.madiyar.vento.entity.Organization;
import dev.madiyar.vento.entity.User;
import dev.madiyar.vento.repository.OrganizationRepository;
import dev.madiyar.vento.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDto> getAll(){
        List<UserResponseDto> result = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            result.add(UserMapper.toDto(user));
        }

        return result;
    }


    public UserResponseDto getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        return UserMapper.toDto(user);
    }

    public UserResponseDto getUserByID(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return UserMapper.toDto(user);
    }
}
