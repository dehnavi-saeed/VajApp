package app.vaj.library.application.command;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RestoreLibraryCommand(
        @NotNull UUID libraryId
) {}