package dev.madiyar.vento.repository;

import dev.madiyar.vento.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository  extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
