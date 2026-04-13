package dev.madiyar.vento.repository;

import dev.madiyar.vento.entity.UserToken;
import dev.madiyar.vento.entity.User;
import dev.madiyar.vento.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, UUID> {
    Optional<UserToken> findByTokenAndType(String token, TokenType type);
    void deleteByUserAndType(User user, TokenType type);
}
