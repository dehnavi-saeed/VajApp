package app.vaj.user.domain;

import java.time.Instant;
import java.util.UUID;

public class UserProfile {

    private final UUID id;
    private final UUID userId;
    private String displayName;
    private String bio;
    private String avatarUrl;
    private Instant createdAt;
    private Instant updatedAt;

    private UserProfile(UUID id, UUID userId) {
        this.id = id;
        this.userId = userId;
    }

    public static UserProfile create(UUID id, UUID userId) {
        UserProfile profile = new UserProfile(id, userId);
        Instant now = Instant.now();
        profile.createdAt = now;
        profile.updatedAt = now;
        return profile;
    }

    public void updateDisplayName(String displayName) {
        if (displayName != null && (displayName.length() < 1 || displayName.length() > 100)) {
            throw new app.vaj.common.domain.DomainValidationException(
                "INVALID_DISPLAY_NAME", java.util.List.of("Display name must be 1-100 characters."));
        }
        this.displayName = displayName;
        this.updatedAt = Instant.now();
    }

    public void updateBio(String bio) {
        if (bio != null && bio.length() > 500) {
            throw new app.vaj.common.domain.DomainValidationException(
                "INVALID_BIO", java.util.List.of("Bio must be at most 500 characters."));
        }
        this.bio = bio;
        this.updatedAt = Instant.now();
    }

    public void updateAvatar(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        this.updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getDisplayName() { return displayName; }
    public String getBio() { return bio; }
    public String getAvatarUrl() { return avatarUrl; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}