package app.vaj.user.infrastructure.persistence;

import app.vaj.user.domain.UserProfile;
import app.vaj.user.domain.repository.UserProfileRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserProfileRepositoryImpl implements UserProfileRepository {

    private final JpaUserProfileRepository jpaRepository;

    public UserProfileRepositoryImpl(JpaUserProfileRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public UserProfile save(UserProfile profile) {
        UserProfileEntity entity = toEntity(profile);
        jpaRepository.save(entity);
        return profile;
    }

    @Override
    public Optional<UserProfile> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).map(this::toDomain);
    }

    private UserProfile toDomain(UserProfileEntity entity) {
        UserProfile profile = UserProfile.create(entity.getId(), entity.getUserId());
        if (entity.getDisplayName() != null) profile.updateDisplayName(entity.getDisplayName());
        if (entity.getBio() != null) profile.updateBio(entity.getBio());
        if (entity.getAvatarUrl() != null) profile.updateAvatar(entity.getAvatarUrl());
        return profile;
    }

    private UserProfileEntity toEntity(UserProfile profile) {
        UserProfileEntity entity = new UserProfileEntity();
        entity.setId(profile.getId());
        entity.setUserId(profile.getUserId());
        entity.setDisplayName(profile.getDisplayName());
        entity.setBio(profile.getBio());
        entity.setAvatarUrl(profile.getAvatarUrl());
        entity.setCreatedAt(profile.getCreatedAt());
        entity.setUpdatedAt(profile.getUpdatedAt());
        return entity;
    }
}