package app.vaj.highlight.domain.event;

import app.vaj.common.domain.event.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class HighlightCreated extends DomainEvent {
    private final UUID highlightId;
    private final UUID bookId;
    private final UUID userId;

    public HighlightCreated(UUID eventId, Instant occurredAt, UUID highlightId, UUID bookId, UUID userId) {
        super(eventId, occurredAt);
        this.highlightId = highlightId;
        this.bookId = bookId;
        this.userId = userId;
    }

    public UUID getHighlightId() { return highlightId; }
    public UUID getBookId() { return bookId; }
    public UUID getUserId() { return userId; }
}