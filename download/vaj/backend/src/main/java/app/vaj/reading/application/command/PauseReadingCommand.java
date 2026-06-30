package app.vaj.reading.application.command;

import jakarta.validation.constraints.Min;

public record PauseReadingCommand(
        @Min(1) int endPage,
        @Min(1) int durationMinutes
) {}