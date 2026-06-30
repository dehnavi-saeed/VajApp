package app.vaj.auth.domain.event;

import app.vaj.common.domain.event.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class UserLoggedIn extends DomainEvent {

    private final UUID userId;

    public UserLoggedIn(UUID eventId, Instant occurredAt, UUID userId) {
        super(eventId, occurredAt);
        this.userId = userId;
    }

    public UUID getUserId() { return userId; }
}