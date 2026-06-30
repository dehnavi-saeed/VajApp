package app.vaj.publisher.domain;

import app.vaj.common.domain.BaseAggregateRoot;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

public class Publisher extends BaseAggregateRoot {

    private String name;

    private Publisher(UUID id) {
        super(id);
    }

    public static Publisher create(UUID id, String name, Clock clock) {
        if (name == null || name.isBlank() || name.length() > 200) {
            throw new app.vaj.common.domain.DomainValidationException(
                "INVALID_PUBLISHER_NAME", java.util.List.of("Publisher name is required (max 200 chars)."));
        }
        Publisher publisher = new Publisher(id);
        publisher.name = name;
        publisher.markCreated(Instant.now(clock), null);
        return publisher;
    }

    public void update(String name, UUID userId, Clock clock) {
        if (name != null) this.name = name;
        markUpdated(Instant.now(clock), userId);
    }

    public String getName() { return name; }
}