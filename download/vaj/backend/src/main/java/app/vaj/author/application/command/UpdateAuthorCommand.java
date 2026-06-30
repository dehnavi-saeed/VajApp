package app.vaj.author.application.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateAuthorCommand(
        @NotNull UUID id,
        @Size(min = 2, max = 200) String name,
        @Size(max = 5000) String bio
) {}