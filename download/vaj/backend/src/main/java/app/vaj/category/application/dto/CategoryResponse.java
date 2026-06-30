package app.vaj.category.application.dto;

import java.time.Instant;
import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        String description,
        int displayOrder,
        String status,
        Instant createdAt,
        Instant updatedAt
) {}