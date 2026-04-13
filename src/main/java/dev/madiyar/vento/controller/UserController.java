package dev.madiyar.vento.controller;


import dev.madiyar.vento.dto.UserResponseDto;
import dev.madiyar.vento.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/me")
    public UserResponseDto getMe(Authentication authentication){
        String email = authentication.getName();
        return userService.getByEmail(email);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable("id") UUID id){
        return userService.getUserByID(id);
    }

}

