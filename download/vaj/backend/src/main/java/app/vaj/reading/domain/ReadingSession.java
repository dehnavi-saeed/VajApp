package app.vaj.reading.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReadingSession extends BaseAggregateRoot {

    private UUID bookId;
    private UUID userId;
    private Integer startPage;
    private Integer endPage;
    private Integer durationMinutes;
    private ReadingState state;

    private ReadingSession(UUID id) { super(id); }

    public static ReadingSession create(UUID id, UUID bookId, UUID userId, int startPage, Clock clock) {
        ReadingSession session = new ReadingSession(id);
        session.bookId = bookId;
        session.userId = userId;
        session.startPage = startPage;
        session.durationMinutes = 0;
        session.state = ReadingState.STARTED;
        session.markCreated(Instant.now(clock), userId);
        return session;
    }

    public void pause(int endPage, int durationMinutes, Clock clock) {
        validateStateTransition(ReadingState.PAUSED);
        if (endPage < startPage) {
            throw new DomainValidationException("INVALID_PAGE_RANGE",
                    List.of("End page must be >= start page."));
        }
        this.endPage = endPage;
        this.durationMinutes = durationMinutes;
        this.state = ReadingState.PAUSED;
        markUpdated(Instant.now(clock), userId);
    }

    public void resume(int startPage, Clock clock) {
        validateStateTransition(ReadingState.STARTED);
        if (startPage > 0) this.startPage = startPage;
        this.state = ReadingState.STARTED;
        markUpdated(Instant.now(clock), userId);
    }

    public void finish(int endPage, int durationMinutes, Clock clock) {
        if (endPage < startPage) {
            throw new DomainValidationException("INVALID_PAGE_RANGE",
                    List.of("End page must be >= start page."));
        }
        this.endPage = endPage;
        this.durationMinutes = durationMinutes;
        this.state = ReadingState.COMPLETED;
        markUpdated(Instant.now(clock), userId);
    }

    public void cancel(Clock clock) {
        this.state = ReadingState.CANCELLED;
        markUpdated(Instant.now(clock), userId);
    }

    private void validateStateTransition(ReadingState target) {
        if (this.state == ReadingState.COMPLETED) {
            throw new DomainValidationException("INVALID_STATE",
                    List.of("Cannot modify a completed session."));
        }
        if (this.state == ReadingState.CANCELLED) {
            throw new DomainValidationException("INVALID_STATE",
                    List.of("Cannot modify a cancelled session."));
        }
        if (target == ReadingState.PAUSED && this.state != ReadingState.STARTED) {
            throw new DomainValidationException("INVALID_STATE",
                    List.of("Only started sessions can be paused."));
        }
        if (target == ReadingState.STARTED && this.state != ReadingState.PAUSED) {
            throw new DomainValidationException("INVALID_STATE",
                    List.of("Only paused sessions can be resumed."));
        }
    }

    public UUID getBookId() { return bookId; }
    public UUID getUserId() { return userId; }
    public Integer getStartPage() { return startPage; }
    public Integer getEndPage() { return endPage; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public ReadingState getState() { return state; }
}