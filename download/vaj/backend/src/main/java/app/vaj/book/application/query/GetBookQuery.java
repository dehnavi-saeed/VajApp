package app.vaj.book.application.query;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GetBookQuery(
        @NotNull UUID id
) {}