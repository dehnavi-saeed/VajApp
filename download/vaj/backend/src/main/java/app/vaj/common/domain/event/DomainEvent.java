package app.vaj.common.domain.event;

import java.time.Instant;
import java.util.UUID;

public abstract class DomainEvent {

    private final UUID eventId;
    private final Instant occurredAt;

    protected DomainEvent(UUID eventId, Instant occurredAt) {
        this.eventId = eventId;
        this.occurredAt = occurredAt;
    }

    public UUID getEventId() { return eventId; }

    public Instant getOccurredAt() { return occurredAt; }
}