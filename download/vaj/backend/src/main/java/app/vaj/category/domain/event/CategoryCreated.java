package app.vaj.category.domain.event;

import app.vaj.common.domain.event.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record CategoryCreated(
        UUID eventId, Instant occurredOn, UUID categoryId, String name
) implements DomainEvent {
}