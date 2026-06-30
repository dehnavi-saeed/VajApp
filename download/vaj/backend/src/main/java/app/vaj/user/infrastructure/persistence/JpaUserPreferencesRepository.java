package app.vaj.user.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserPreferencesRepository extends JpaRepository<UserPreferencesEntity, UUID> {
    Optional<UserPreferencesEntity> findByUserId(UUID userId);
}