package dev.madiyar.vento.dto.mapper;

import dev.madiyar.vento.dto.UserResponse;
import dev.madiyar.vento.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", source = "role")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "organizationName", source = "organization.name")
    UserResponse toDto(User user);
}
