package app.vaj.goal.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;
import java.time.*;
import java.util.*;

public class ReadingGoal extends BaseAggregateRoot {
    private UUID userId;
    private GoalType type;
    private int target;
    private int currentProgress;
    private Instant startDate;
    private Instant endDate;
    private GoalStatus status;

    private ReadingGoal(UUID id) { super(id); }

    public static ReadingGoal create(UUID id, UUID userId, GoalType type, int target, Instant startDate, Instant endDate, Clock clock) {
        if (target < 1) throw new DomainValidationException("INVALID_TARGET", List.of("Target must be positive."));
        if (endDate != null && startDate != null && !endDate.isAfter(startDate)) {
            throw new DomainValidationException("INVALID_DATES", List.of("End date must be after start date."));
        }
        ReadingGoal g = new ReadingGoal(id);
        g.userId = userId; g.type = type; g.target = target; g.currentProgress = 0;
        g.startDate = startDate; g.endDate = endDate; g.status = GoalStatus.ACTIVE;
        g.markCreated(Instant.now(clock), userId);
        return g;
    }

    public void update(int target, Instant startDate, Instant endDate, Clock clock) {
        if (target > 0) this.target = target;
        if (startDate != null) this.startDate = startDate;
        if (endDate != null) this.endDate = endDate;
        markUpdated(Instant.now(clock), userId);
    }

    public void pause(Clock clock) { this.status = GoalStatus.PAUSED; markUpdated(Instant.now(clock), userId); }
    public void resume(Clock clock) { this.status = GoalStatus.ACTIVE; markUpdated(Instant.now(clock), userId); }
    public void complete(Clock clock) { this.status = GoalStatus.COMPLETED; markUpdated(Instant.now(clock), userId); }
    public void cancel(Clock clock) { this.status = GoalStatus.CANCELLED; markUpdated(Instant.now(clock), userId); }

    public UUID getUserId() { return userId; }
    public GoalType getType() { return type; }
    public int getTarget() { return target; }
    public int getCurrentProgress() { return currentProgress; }
    public Instant getStartDate() { return startDate; }
    public Instant getEndDate() { return endDate; }
    public GoalStatus getStatus() { return status; }
}