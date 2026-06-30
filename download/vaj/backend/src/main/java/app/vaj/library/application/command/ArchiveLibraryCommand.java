package app.vaj.library.application.command;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ArchiveLibraryCommand(
        @NotNull UUID libraryId
) {}