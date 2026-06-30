package app.vaj.user.application.command;

import jakarta.validation.constraints.NotBlank;

public record UpdatePreferencesCommand(
        String language,
        @NotBlank String timezone,
        String theme
) {}