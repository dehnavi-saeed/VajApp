package app.vaj.reading.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReadingSession extends BaseAggregateRoot {

    private UUID bookId;
    private Integer startPage;
    private Integer endPage;
    private Long durationMs;
    private Instant startedAt;
    private Instant endedAt;

    private ReadingSession(UUID id) { super(id); }

    public static ReadingSession start(UUID id, UUID bookId, Integer startPage, Clock clock) {
        List<String> errors = new ArrayList<>();
        if (startPage != null && startPage <= 0) errors.add("Start page must be positive.");
        if (!errors.isEmpty()) throw new DomainValidationException("INVALID_SESSION", errors);

        ReadingSession session = new ReadingSession(id);
        session.bookId = bookId;
        session.startPage = startPage;
        session.startedAt = Instant.now(clock);
        session.markCreated(session.startedAt, null);
        return session;
    }

    public void end(Integer endPage, Clock clock) {
        List<String> errors = new ArrayList<>();
        if (endPage != null && endPage <= 0) errors.add("End page must be positive.");
        if (endPage != null && startPage != null && endPage < startPage) errors.add("End page cannot be before start page.");
        if (!errors.isEmpty()) throw new DomainValidationException("INVALID_SESSION_END", errors);

        this.endPage = endPage;
        this.endedAt = Instant.now(clock);
        if (startedAt != null) {
            this.durationMs = Duration.between(startedAt, endedAt).toMillis();
        }
        markUpdated(endedAt, getId());
    }

    public UUID getBookId() { return bookId; }
    public Integer getStartPage() { return startPage; }
    public Integer getEndPage() { return endPage; }
    public Long getDurationMs() { return durationMs; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getEndedAt() { return endedAt; }
    public boolean isActive() { return endedAt == null; }
}