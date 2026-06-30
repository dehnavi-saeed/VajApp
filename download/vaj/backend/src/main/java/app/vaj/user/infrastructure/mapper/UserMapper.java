package app.vaj.user.infrastructure.mapper;

import app.vaj.user.application.dto.PreferencesResponse;
import app.vaj.user.application.dto.ProfileResponse;
import app.vaj.user.domain.UserPreferences;
import app.vaj.user.domain.UserProfile;
import app.vaj.user.infrastructure.persistence.UserPreferencesEntity;
import app.vaj.user.infrastructure.persistence.UserProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ProfileResponse toResponse(UserProfile profile);

    PreferencesResponse toResponse(UserPreferences preferences);

    UserProfileEntity toEntity(UserProfile profile);

    UserPreferencesEntity toEntity(UserPreferences preferences);

    void updateEntity(@MappingTarget UserProfileEntity entity, UserProfile profile);

    void updateEntity(@MappingTarget UserPreferencesEntity entity, UserPreferences preferences);
}