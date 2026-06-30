package app.vaj.collection.application.dto;
import java.time.Instant;
import java.util.UUID;
public record CollectionResponse(UUID id, String name, String description, String status, Instant createdAt) {}