package dev.madiyar.vento.dto.mapper;

import dev.madiyar.vento.dto.UserResponseDto;
import dev.madiyar.vento.entity.User;

// spring has builit support of mappers ConversionService
// @Component public UserConverter implement ConversionService<User, UserResponseDto {}
public class UserMapper {

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getRole().name(),
                user.getStatus().name(),
                user.getCreatedAt(),
                user.isEmailVerified(),
                user.getOrganization() != null ? user.getOrganization().getName() : null,
                user.getUpdatedAt()
        );
    }
}
