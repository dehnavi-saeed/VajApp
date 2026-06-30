package app.vaj.user.infrastructure.persistence;

import app.vaj.user.domain.UserPreferences;
import app.vaj.user.domain.repository.UserPreferencesRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserPreferencesRepositoryImpl implements UserPreferencesRepository {

    private final JpaUserPreferencesRepository jpaRepository;

    public UserPreferencesRepositoryImpl(JpaUserPreferencesRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public UserPreferences save(UserPreferences prefs) {
        UserPreferencesEntity entity = toEntity(prefs);
        jpaRepository.save(entity);
        return prefs;
    }

    @Override
    public Optional<UserPreferences> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).map(this::toDomain);
    }

    private UserPreferences toDomain(UserPreferencesEntity entity) {
        UserPreferences prefs = UserPreferences.create(entity.getId(), entity.getUserId(), entity.getTimezone());
        prefs.updateLanguage(entity.getLanguage());
        prefs.updateTheme(entity.getTheme());
        return prefs;
    }

    private UserPreferencesEntity toEntity(UserPreferences prefs) {
        UserPreferencesEntity entity = new UserPreferencesEntity();
        entity.setId(prefs.getId());
        entity.setUserId(prefs.getUserId());
        entity.setLanguage(prefs.getLanguage());
        entity.setTimezone(prefs.getTimezone());
        entity.setTheme(prefs.getTheme());
        entity.setCreatedAt(prefs.getCreatedAt());
        entity.setUpdatedAt(prefs.getUpdatedAt());
        return entity;
    }
}