package app.vaj.publisher.application.dto;

import java.time.Instant;
import java.util.UUID;

public record PublisherResponse(
        UUID id,
        String name,
        String website,
        String country,
        String status,
        Instant createdAt,
        Instant updatedAt
) {}