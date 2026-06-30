package app.vaj.user.domain.repository;

import app.vaj.user.domain.UserProfile;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository {
    UserProfile save(UserProfile profile);
    Optional<UserProfile> findByUserId(UUID userId);
}