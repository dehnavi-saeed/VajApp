package app.vaj.publisher.domain.event;

import app.vaj.common.domain.event.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class PublisherCreated extends DomainEvent {

    private final UUID publisherId;
    private final String name;

    public PublisherCreated(UUID eventId, Instant occurredAt, UUID publisherId, String name) {
        super(eventId, occurredAt);
        this.publisherId = publisherId;
        this.name = name;
    }

    public UUID getPublisherId() { return publisherId; }
    public String getName() { return name; }
}