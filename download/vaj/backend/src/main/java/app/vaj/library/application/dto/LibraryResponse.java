package app.vaj.library.application.dto;

import java.time.Instant;
import java.util.UUID;

public record LibraryResponse(
        UUID id,
        UUID userId,
        String name,
        String description,
        String status,
        Instant createdAt,
        Instant updatedAt
) {}