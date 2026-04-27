package dev.madiyar.vento.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest (@NotBlank(message = "email is required ") String email, String name, String surname){

}

