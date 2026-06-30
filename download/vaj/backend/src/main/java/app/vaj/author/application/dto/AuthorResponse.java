package app.vaj.author.application.dto;

import java.time.Instant;
import java.util.UUID;

public record AuthorResponse(
        UUID id,
        String name,
        String bio,
        String type,
        String status,
        Instant createdAt,
        Instant updatedAt
) {}