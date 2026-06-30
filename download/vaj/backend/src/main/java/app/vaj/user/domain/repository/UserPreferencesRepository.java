package app.vaj.user.domain.repository;

import app.vaj.user.domain.UserPreferences;

import java.util.Optional;
import java.util.UUID;

public interface UserPreferencesRepository {
    UserPreferences save(UserPreferences preferences);
    Optional<UserPreferences> findByUserId(UUID userId);
}