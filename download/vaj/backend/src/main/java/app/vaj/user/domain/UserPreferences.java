package app.vaj.user.domain;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class UserPreferences {

    private static final Set<String> SUPPORTED_LANGUAGES = Set.of("en", "fa", "ar", "fr", "de", "es");
    private static final Set<String> SUPPORTED_THEMES = Set.of("LIGHT", "DARK", "SYSTEM");

    private final UUID id;
    private final UUID userId;
    private String language;
    private String timezone;
    private String theme;
    private Instant createdAt;
    private Instant updatedAt;

    private UserPreferences(UUID id, UUID userId) {
        this.id = id;
        this.userId = userId;
    }

    public static UserPreferences create(UUID id, UUID userId, String timezone) {
        UserPreferences prefs = new UserPreferences(id, userId);
        prefs.language = "en";
        prefs.timezone = timezone != null ? timezone : "UTC";
        prefs.theme = "LIGHT";
        Instant now = Instant.now();
        prefs.createdAt = now;
        prefs.updatedAt = now;
        return prefs;
    }

    public void updateLanguage(String language) {
        if (!SUPPORTED_LANGUAGES.contains(language)) {
            throw new app.vaj.common.domain.DomainValidationException(
                "UNSUPPORTED_LANGUAGE", java.util.List.of("Language '" + language + "' is not supported."));
        }
        this.language = language;
        this.updatedAt = Instant.now();
    }

    public void updateTimezone(String timezone) {
        if (timezone == null || timezone.isBlank()) {
            throw new app.vaj.common.domain.DomainValidationException(
                "INVALID_TIMEZONE", java.util.List.of("Timezone is required."));
        }
        this.timezone = timezone;
        this.updatedAt = Instant.now();
    }

    public void updateTheme(String theme) {
        if (!SUPPORTED_THEMES.contains(theme)) {
            throw new app.vaj.common.domain.DomainValidationException(
                "INVALID_THEME", java.util.List.of("Theme must be LIGHT, DARK, or SYSTEM."));
        }
        this.theme = theme;
        this.updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getLanguage() { return language; }
    public String getTimezone() { return timezone; }
    public String getTheme() { return theme; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}