package app.vaj.highlight.application.command;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteHighlightCommand(
        @NotNull UUID highlightId
) {}