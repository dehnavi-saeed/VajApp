package app.vaj.reading.application.command;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record StartReadingCommand(
        @NotNull UUID bookId,
        @Min(1) int startPage
) {}