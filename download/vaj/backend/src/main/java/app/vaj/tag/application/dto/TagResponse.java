package app.vaj.tag.application.dto;
import java.time.Instant;
import java.util.UUID;
public record TagResponse(UUID id, String name, String color, String description, String status, Instant createdAt) {}