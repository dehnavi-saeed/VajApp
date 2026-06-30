package app.vaj.auth.domain.repository;

import app.vaj.auth.domain.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken token);

    Optional<RefreshToken> findByToken(String token);

    void deleteByUserId(UUID userId);

    void deleteExpiredTokens();
}