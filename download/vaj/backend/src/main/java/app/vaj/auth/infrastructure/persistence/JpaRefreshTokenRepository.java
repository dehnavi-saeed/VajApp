package app.vaj.auth.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface JpaRefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

    Optional<RefreshTokenEntity> findByToken(String token);

    @Modifying
    @Query("UPDATE RefreshTokenEntity r SET r.revokedAt = :now WHERE r.id = :id")
    void revokeToken(UUID id, Instant now);

    default void revokeToken(UUID id, Clock clock) {
        revokeToken(id, Instant.now(clock));
    }

    @Modifying
    @Query("DELETE FROM RefreshTokenEntity r WHERE r.userId = :userId")
    void deleteAllByUserId(UUID userId);

    default void saveRefreshToken(UUID userId, String token, Instant expiresAt, Clock clock) {
        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setId(UUID.randomUUID());
        entity.setUserId(userId);
        entity.setToken(token);
        entity.setExpiresAt(expiresAt);
        entity.setCreatedAt(Instant.now(clock));
        save(entity);
    }
}