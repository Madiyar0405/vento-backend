package dev.madiyar.vento.service;


import dev.madiyar.vento.dto.UserResponse;
import dev.madiyar.vento.dto.mapper.UserMapper;
import dev.madiyar.vento.entity.User;
import dev.madiyar.vento.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponse> getAll(){
        List<UserResponse> result = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            result.add(userMapper.toDto(user));
        }

        return result;
    }


    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        return userMapper.toDto(user);
    }

    public UserResponse getUserByID(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return userMapper.toDto(user);
    }
}
