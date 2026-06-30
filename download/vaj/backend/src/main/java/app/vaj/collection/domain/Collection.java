package app.vaj.collection.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Collection extends BaseAggregateRoot {

    private UUID userId;
    private String name;
    private String description;
    private List<UUID> bookIds = new ArrayList<>();

    private Collection(UUID id) { super(id); }

    public static Collection create(UUID id, UUID userId, String name, String description, Clock clock) {
        if (name == null || name.isBlank() || name.length() > 100) {
            throw new DomainValidationException("INVALID_COLLECTION_NAME",
                    List.of("Collection name is required (max 100 chars)."));
        }
        Collection c = new Collection(id);
        c.userId = userId;
        c.name = name;
        c.description = description;
        c.markCreated(Instant.now(clock), userId);
        return c;
    }

    public void addBook(UUID bookId, Clock clock) {
        if (!bookIds.contains(bookId)) {
            bookIds.add(bookId);
            markUpdated(Instant.now(clock), userId);
        }
    }

    public void removeBook(UUID bookId, Clock clock) {
        bookIds.remove(bookId);
        markUpdated(Instant.now(clock), userId);
    }

    public void rename(String name, Clock clock) {
        if (name != null && !name.isBlank()) this.name = name;
        markUpdated(Instant.now(clock), userId);
    }

    public UUID getUserId() { return userId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<UUID> getBookIds() { return List.copyOf(bookIds); }
}