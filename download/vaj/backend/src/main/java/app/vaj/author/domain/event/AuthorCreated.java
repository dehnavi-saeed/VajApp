package app.vaj.author.domain.event;

import app.vaj.common.domain.event.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class AuthorCreated extends DomainEvent {

    private final UUID authorId;
    private final String name;

    public AuthorCreated(UUID eventId, Instant occurredAt, UUID authorId, String name) {
        super(eventId, occurredAt);
        this.authorId = authorId;
        this.name = name;
    }

    public UUID getAuthorId() { return authorId; }
    public String getName() { return name; }
}