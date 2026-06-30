package app.vaj.book.application.command;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateBookCommand(
        UUID id,
        @Size(min = 3, max = 500) String title,
        @Size(max = 500) String subtitle,
        String description,
        String language,
        @Min(1) @Max(99999) Integer pageCount,
        @Size(max = 20) String isbn,
        UUID publisherId
) {}