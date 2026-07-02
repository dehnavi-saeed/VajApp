package app.vaj.category.domain.event;

import app.vaj.common.domain.event.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class CategoryCreated extends DomainEvent {

    private final UUID categoryId;
    private final String name;

    public CategoryCreated(UUID eventId, Instant occurredAt, UUID categoryId, String name) {
        super(eventId, occurredAt);
        this.categoryId = categoryId;
        this.name = name;
    }

    public UUID getCategoryId() { return categoryId; }
    public String getName() { return name; }
}