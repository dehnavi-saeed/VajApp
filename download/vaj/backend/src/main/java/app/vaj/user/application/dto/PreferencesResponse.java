package app.vaj.user.application.dto;

import java.util.UUID;

public record PreferencesResponse(
        UUID id,
        UUID userId,
        String language,
        String timezone,
        String theme
) {}