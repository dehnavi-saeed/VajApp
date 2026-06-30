package app.vaj.user.application.command;

import jakarta.validation.constraints.Size;

public record UpdateProfileCommand(
        @Size(max = 100) String displayName,
        @Size(max = 500) String bio
) {}