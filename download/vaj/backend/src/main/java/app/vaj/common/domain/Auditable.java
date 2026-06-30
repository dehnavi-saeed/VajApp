package app.vaj.common.domain;

import java.time.Instant;

public interface Auditable {
    Instant getCreatedAt();
    Instant getUpdatedAt();
    Instant getDeletedAt();
    Long getVersion();
}