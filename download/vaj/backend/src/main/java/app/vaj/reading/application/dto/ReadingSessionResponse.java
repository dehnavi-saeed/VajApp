package app.vaj.reading.application.dto;

import java.time.Instant;
import java.util.UUID;

public record ReadingSessionResponse(
        UUID id, UUID bookId, Integer startPage, Integer endPage,
        Integer durationMinutes, String state, Instant createdAt, Instant updatedAt
) {}