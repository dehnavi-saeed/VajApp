package app.vaj.bookmark.application.command;

import jakarta.validation.constraints.Min;
import java.util.UUID;

public record UpdateBookmarkCommand(
        String title,
        String description,
        String color,
        @Min(1) Integer page
) {}