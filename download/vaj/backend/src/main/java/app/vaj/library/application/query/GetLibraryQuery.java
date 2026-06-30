package app.vaj.library.application.query;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GetLibraryQuery(
        @NotNull UUID libraryId
) {}