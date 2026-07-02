package app.vaj.readingstate.application.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ReadingStateResponse(
        UUID id,
        UUID bookId,
        UUID userId,
        int currentPage,
        int totalPagesRead,
        BigDecimal progressPercentage,
        int totalReadingMinutes,
        UUID lastSessionId,
        Instant lastReadAt,
        Instant estimatedFinishDate,
        String status,
        Instant updatedAt
) {}