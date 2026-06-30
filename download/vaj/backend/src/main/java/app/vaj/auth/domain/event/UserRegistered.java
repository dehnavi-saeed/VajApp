package app.vaj.auth.domain.event;

import app.vaj.common.domain.event.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class UserRegistered extends DomainEvent {

    private final UUID userId;
    private final String email;
    private final String username;

    public UserRegistered(UUID eventId, Instant occurredAt, UUID userId, String email, String username) {
        super(eventId, occurredAt);
        this.userId = userId;
        this.email = email;
        this.username = username;
    }

    public UUID getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
}