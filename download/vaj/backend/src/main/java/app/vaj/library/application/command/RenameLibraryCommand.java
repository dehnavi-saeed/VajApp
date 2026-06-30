package app.vaj.library.application.command;

import jakarta.validation.constraints.Size;

public record RenameLibraryCommand(
        @Size(min = 3, max = 100) String name
) {}