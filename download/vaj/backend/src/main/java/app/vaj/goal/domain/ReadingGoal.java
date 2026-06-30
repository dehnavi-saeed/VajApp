package app.vaj.goal.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReadingGoal extends BaseAggregateRoot {

    public enum GoalType { DAILY, WEEKLY, MONTHLY, YEARLY }
    public enum GoalStatus { ACTIVE, COMPLETED, ABANDONED }

    private UUID userId;
    private UUID libraryId;
    private String title;
    private GoalType goalType;
    private int targetValue;
    private int currentValue;
    private LocalDate startDate;
    private LocalDate endDate;
    private GoalStatus status;

    private ReadingGoal(UUID id) { super(id); }

    public static ReadingGoal create(UUID id, UUID userId, UUID libraryId, String title,
                                      GoalType goalType, int targetValue,
                                      LocalDate startDate, LocalDate endDate, Clock clock) {
        List<String> errors = new ArrayList<>();
        if (title == null || title.isBlank()) errors.add("Title is required.");
        if (targetValue <= 0) errors.add("Target must be positive.");
        if (startDate == null) errors.add("Start date is required.");
        if (endDate == null) errors.add("End date is required.");
        if (startDate != null && endDate != null && endDate.isBefore(startDate))
            errors.add("End date must be after start date.");
        if (!errors.isEmpty()) throw new DomainValidationException("INVALID_GOAL", errors);

        ReadingGoal goal = new ReadingGoal(id);
        goal.userId = userId;
        goal.libraryId = libraryId;
        goal.title = title;
        goal.goalType = goalType;
        goal.targetValue = targetValue;
        goal.currentValue = 0;
        goal.startDate = startDate;
        goal.endDate = endDate;
        goal.status = GoalStatus.ACTIVE;
        goal.markCreated(Instant.now(clock), userId);
        return goal;
    }

    public void updateProgress(int additionalValue, Clock clock) {
        if (status != GoalStatus.ACTIVE) return;
        this.currentValue = Math.min(currentValue + additionalValue, targetValue);
        if (this.currentValue >= targetValue) {
            this.status = GoalStatus.COMPLETED;
        }
        markUpdated(Instant.now(clock), userId);
    }

    public void abandon(Clock clock) {
        this.status = GoalStatus.ABANDONED;
        markUpdated(Instant.now(clock), userId);
    }

    public double getProgressPercentage() {
        if (targetValue == 0) return 0;
        return Math.min(100.0, ((double) currentValue / targetValue) * 100);
    }

    public UUID getUserId() { return userId; }
    public UUID getLibraryId() { return libraryId; }
    public String getTitle() { return title; }
    public GoalType getGoalType() { return goalType; }
    public int getTargetValue() { return targetValue; }
    public int getCurrentValue() { return currentValue; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public GoalStatus getStatus() { return status; }
}