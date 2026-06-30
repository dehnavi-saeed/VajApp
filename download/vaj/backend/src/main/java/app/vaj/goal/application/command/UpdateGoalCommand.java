package app.vaj.goal.application.command;
import jakarta.validation.constraints.Min;
import java.time.Instant;
public record UpdateGoalCommand(@Min(1) Integer target, Instant startDate, Instant endDate) {}