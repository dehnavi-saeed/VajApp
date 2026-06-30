package app.vaj.library.application.command;

import jakarta.validation.constraints.Size;

public record UpdateLibraryCommand(
        @Size(min = 3, max = 100) String name,
        @Size(max = 1000) String description
) {}