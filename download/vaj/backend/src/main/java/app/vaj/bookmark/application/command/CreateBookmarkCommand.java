package app.vaj.bookmark.application.command;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateBookmarkCommand(
        @NotNull UUID bookId,
        @Min(1) int page,
        String title,
        String description,
        String color
) {}