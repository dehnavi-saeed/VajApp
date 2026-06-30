package app.vaj.library.domain.event;

import app.vaj.common.domain.event.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class LibraryCreated extends DomainEvent {
    private final UUID libraryId;
    private final UUID userId;
    private final String name;

    public LibraryCreated(UUID eventId, Instant occurredAt, UUID libraryId, UUID userId, String name) {
        super(eventId, occurredAt);
        this.libraryId = libraryId;
        this.userId = userId;
        this.name = name;
    }

    public UUID getLibraryId() { return libraryId; }
    public UUID getUserId() { return userId; }
    public String getName() { return name; }
}