package app.vaj.tag.domain;

import java.time.Instant;
import java.util.UUID;

public class Tag {

    private final UUID id;
    private final UUID userId;
    private String name;
    private Instant createdAt;

    private Tag(UUID id, UUID userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.createdAt = Instant.now();
    }

    public static Tag create(UUID id, UUID userId, String name) {
        if (name == null || name.isBlank() || name.length() > 100) {
            throw new app.vaj.common.domain.DomainValidationException(
                "INVALID_TAG_NAME", java.util.List.of("Tag name is required (max 100 chars)."));
        }
        return new Tag(id, userId, name);
    }

    public void rename(String name) { this.name = name; }
    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getName() { return name; }
    public Instant getCreatedAt() { return createdAt; }
}