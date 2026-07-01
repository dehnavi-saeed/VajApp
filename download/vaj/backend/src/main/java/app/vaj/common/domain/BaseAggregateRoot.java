package app.vaj.common.domain;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class BaseAggregateRoot implements Auditable {

    private final UUID id;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private UUID deletedBy;
    private Long version;
    private final List<Object> domainEvents = new ArrayList<>();

    protected BaseAggregateRoot(UUID id) {
        this.id = id;
    }

    protected BaseAggregateRoot(UUID id, Clock clock) {
        this.id = id;
        this.createdAt = Instant.now(clock);
        this.updatedAt = Instant.now(clock);
    }

    public UUID getId() {
        return id;
    }

    @Override
    public Instant getCreatedAt() { return createdAt; }

    @Override
    public Instant getUpdatedAt() { return updatedAt; }

    @Override
    public Instant getDeletedAt() { return deletedAt; }

    public UUID getCreatedBy() { return createdBy; }

    public UUID getUpdatedBy() { return updatedBy; }

    public UUID getDeletedBy() { return deletedBy; }

    @Override
    public Long getVersion() { return version; }

    public List<Object> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    protected void markCreated(Instant now, UUID userId) {
        this.createdAt = now;
        this.updatedAt = now;
        this.createdBy = userId;
        this.updatedBy = userId;
    }

    protected void markUpdated(Instant now, UUID userId) {
        this.updatedAt = now;
        this.updatedBy = userId;
    }

    protected void markDeleted(Instant now, UUID userId) {
        this.deletedAt = now;
        this.deletedBy = userId;
        this.updatedAt = now;
        this.updatedBy = userId;
    }

    public void registerEvent(Object event) {
        domainEvents.add(event);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }
}