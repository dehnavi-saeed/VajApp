package app.vaj.auth.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserAuthRepository extends JpaRepository<UserAuthEntity, UUID> {

    Optional<UserAuthEntity> findByEmail(String email);

    Optional<UserAuthEntity> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}