package app.vaj.publisher.application.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdatePublisherCommand(
        @NotNull UUID id,
        @Size(min = 2, max = 200) String name,
        @Size(max = 500) String website,
        @Size(max = 3) String country
) {}