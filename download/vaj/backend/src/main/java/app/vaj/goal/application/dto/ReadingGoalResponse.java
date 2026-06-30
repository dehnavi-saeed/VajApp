package app.vaj.goal.application.dto;
import java.time.Instant;
import java.util.UUID;
public record ReadingGoalResponse(
    UUID id, String type, int target, int currentProgress,
    Instant startDate, Instant endDate, String status, Instant createdAt
) {}