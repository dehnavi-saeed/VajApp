package app.vaj.auth.application.dto;

import java.time.Instant;
import java.util.UUID;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        Instant expiresAt,
        UserSummary user
) {
    public record UserSummary(UUID id, String email, String username, String status) {}
}