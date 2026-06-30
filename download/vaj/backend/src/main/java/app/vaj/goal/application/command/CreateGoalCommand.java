package app.vaj.goal.application.command;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
public record CreateGoalCommand(
    @NotNull String type, @Min(1) int target, @NotNull Instant startDate, Instant endDate
) {}