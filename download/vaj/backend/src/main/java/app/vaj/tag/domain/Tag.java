package app.vaj.tag.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;
import java.time.*;
import java.util.*;

public class Tag extends BaseAggregateRoot {
    private UUID userId;
    private String name;
    private String color;
    private String description;
    private TagStatus status;

    private Tag(UUID id) { super(id); }

    public static Tag create(UUID id, UUID userId, String name, String color, String description, Clock clock) {
        if (name == null || name.isBlank() || name.length() < 2 || name.length() > 100)
            throw new DomainValidationException("INVALID_TAG_NAME", List.of("Tag name must be 2-100 chars."));
        Tag t = new Tag(id);
        t.userId = userId; t.name = name; t.color = color; t.description = description; t.status = TagStatus.ACTIVE;
        t.markCreated(Instant.now(clock), userId);
        return t;
    }

    public void update(String name, String color, String description, Clock clock) {
        if (name != null) { if (name.length() < 2 || name.length() > 100) throw new DomainValidationException("INVALID_TAG_NAME", List.of("2-100.")); this.name = name; }
        if (color != null) this.color = color;
        if (description != null) { if (description.length() > 500) throw new DomainValidationException("LONG_DESC", List.of("Max 500.")); this.description = description; }
        markUpdated(Instant.now(clock), userId);
    }

    public void delete(Clock clock) { this.status = TagStatus.DELETED; markDeleted(Instant.now(clock), userId); }

    public UUID getUserId() { return userId; }
    public String getName() { return name; }
    public String getColor() { return color; }
    public String getDescription() { return description; }
    public TagStatus getStatus() { return status; }
}