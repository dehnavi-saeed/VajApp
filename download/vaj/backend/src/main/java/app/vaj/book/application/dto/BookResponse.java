package app.vaj.book.application.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record BookResponse(
        UUID id, UUID libraryId, String title, String subtitle, String isbn,
        String description, String language, Integer pageCount, String format,
        String coverUrl, String status, List<UUID> authorIds, UUID publisherId,
        Instant createdAt, Instant updatedAt
) {}