package app.vaj.book.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CreateBookCommand(
        @NotBlank UUID libraryId,
        @NotBlank @Size(min = 3, max = 500) String title,
        @Size(max = 500) String subtitle,
        @Size(max = 20) String isbn,
        String description,
        String language,
        Integer pageCount,
        String format,
        List<UUID> authorIds,
        UUID publisherId
) {}