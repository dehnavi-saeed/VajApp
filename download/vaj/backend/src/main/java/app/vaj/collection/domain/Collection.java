package app.vaj.collection.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;
import java.time.*;
import java.util.*;

public class Collection extends BaseAggregateRoot {
    private UUID userId;
    private String name;
    private String description;
    private CollectionStatus status;

    private Collection(UUID id) { super(id); }

    public static Collection create(UUID id, UUID userId, String name, String description, Clock clock) {
        if (name == null || name.isBlank() || name.length() < 3 || name.length() > 200)
            throw new DomainValidationException("INVALID_NAME", List.of("Name must be 3-200 chars."));
        Collection c = new Collection(id);
        c.userId = userId; c.name = name; c.description = description; c.status = CollectionStatus.ACTIVE;
        c.markCreated(Instant.now(clock), userId);
        return c;
    }

    public void update(String name, String description, Clock clock) {
        if (name != null) { if (name.length() < 3 || name.length() > 200) throw new DomainValidationException("INVALID_NAME", List.of("Name 3-200.")); this.name = name; }
        if (description != null) { if (description.length() > 1000) throw new DomainValidationException("INVALID_DESC", List.of("Max 1000.")); this.description = description; }
        markUpdated(Instant.now(clock), userId);
    }

    public void archive(Clock clock) { this.status = CollectionStatus.ARCHIVED; markUpdated(Instant.now(clock), userId); }
    public void delete(Clock clock) { this.status = CollectionStatus.DELETED; markDeleted(Instant.now(clock), userId); }

    public UUID getUserId() { return userId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public CollectionStatus getStatus() { return status; }
}