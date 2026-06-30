package app.vaj.user.application.dto;

import java.time.Instant;
import java.util.UUID;

public record ProfileResponse(
        UUID id,
        UUID userId,
        String displayName,
        String bio,
        String avatarUrl,
        Instant createdAt
) {}