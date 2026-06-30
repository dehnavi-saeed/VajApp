package app.vaj.highlight.application.dto;
import java.time.Instant;
import java.util.UUID;
public record HighlightResponse(
    UUID id, UUID bookId, Integer page, Integer startPosition, Integer endPosition,
    String textSnapshot, String color, String comment, String status, Instant createdAt, Instant updatedAt
) {}