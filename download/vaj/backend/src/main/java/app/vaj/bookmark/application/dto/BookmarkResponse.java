package app.vaj.bookmark.application.dto;

import java.time.Instant;
import java.util.UUID;

public record BookmarkResponse(
        UUID id, UUID bookId, Integer page, String title, String description,
        String color, int sortOrder, Instant createdAt
) {}