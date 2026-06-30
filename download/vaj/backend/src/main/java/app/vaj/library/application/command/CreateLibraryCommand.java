package app.vaj.library.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateLibraryCommand(
        @NotBlank @Size(min = 3, max = 100) String name,
        @Size(max = 1000) String description
) {}