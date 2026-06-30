package app.vaj.reading.domain.event;

import app.vaj.common.domain.event.DomainEvent;
import java.time.Instant;
import java.util.UUID;

public class ReadingStarted extends DomainEvent {
    private final UUID sessionId;
    private final UUID bookId;
    private final UUID userId;

    public ReadingStarted(UUID eventId, Instant occurredAt, UUID sessionId, UUID bookId, UUID userId) {
        super(eventId, occurredAt);
        this.sessionId = sessionId;
        this.bookId = bookId;
        this.userId = userId;
    }

    public UUID getSessionId() { return sessionId; }
    public UUID getBookId() { return bookId; }
    public UUID getUserId() { return userId; }
}