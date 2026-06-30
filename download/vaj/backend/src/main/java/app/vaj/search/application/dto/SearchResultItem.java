package app.vaj.search.application.dto;

import java.time.Instant;
import java.util.UUID;

public record SearchResultItem(
    UUID id,
    String type,
    String title,
    String excerpt,
    Instant createdAt
) {}