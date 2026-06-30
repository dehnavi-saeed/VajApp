package app.vaj.auth.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class RefreshToken extends BaseAggregateRoot {

    private UUID userId;
    private String token;
    private Instant expiresAt;
    private Instant revokedAt;

    private RefreshToken(UUID id) {
        super(id);
    }

    public static RefreshToken create(UUID id, UUID userId, String token, Instant expiresAt, Clock clock) {
        if (userId == null || token == null || token.isBlank() || expiresAt == null) {
            throw new DomainValidationException("INVALID_REFRESH_TOKEN", List.of("All fields are required."));
        }
        RefreshToken refreshToken = new RefreshToken(id);
        refreshToken.userId = userId;
        refreshToken.token = token;
        refreshToken.expiresAt = expiresAt;
        refreshToken.markCreated(Instant.now(clock), userId);
        return refreshToken;
    }

    public void revoke(Clock clock) {
        this.revokedAt = Instant.now(clock);
        markUpdated(Instant.now(clock), userId);
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public boolean isRevoked() {
        return revokedAt != null;
    }

    public boolean isValid() {
        return !isExpired() && !isRevoked();
    }

    public UUID getUserId() { return userId; }
    public String getToken() { return token; }
    public Instant getExpiresAt() { return expiresAt; }
}