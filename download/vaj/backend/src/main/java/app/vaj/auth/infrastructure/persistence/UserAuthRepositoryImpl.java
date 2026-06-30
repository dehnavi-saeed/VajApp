package app.vaj.auth.infrastructure.persistence;

import app.vaj.auth.domain.UserAuth;
import app.vaj.auth.domain.UserAuthStatus;
import app.vaj.auth.domain.repository.UserAuthRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserAuthRepositoryImpl implements UserAuthRepository {

    private final JpaUserAuthRepository jpaRepository;

    public UserAuthRepositoryImpl(JpaUserAuthRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public UserAuth save(UserAuth userAuth) {
        UserAuthEntity entity = toEntity(userAuth);
        UserAuthEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<UserAuth> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<UserAuth> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public Optional<UserAuth> findByUsername(String username) {
        return jpaRepository.findByUsername(username).map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    private UserAuth toDomain(UserAuthEntity entity) {
        UserAuth user = new UserAuth(entity.getId());
        user = reconstructUser(user, entity);
        return user;
    }

    private UserAuth reconstructUser(UserAuth user, UserAuthEntity entity) {
        UserAuth result = UserAuth.class.cast(user);
        java.lang.reflect.Field[] fields = UserAuth.class.getDeclaredFields();
        try {
            setField(result, "username", entity.getUsername());
            setField(result, "email", entity.getEmail());
            setField(result, "passwordHash", entity.getPasswordHash());
            setField(result, "status", entity.getStatus() != null ? UserAuthStatus.valueOf(entity.getStatus().name()) : UserAuthStatus.PENDING);
            setField(result, "createdAt", entity.getCreatedAt());
            setField(result, "updatedAt", entity.getUpdatedAt());
            setField(result, "deletedAt", entity.getDeletedAt());
            setField(result, "version", entity.getVersion());
        } catch (Exception e) {
            throw new RuntimeException("Failed to reconstruct UserAuth", e);
        }
        return result;
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private UserAuthEntity toEntity(UserAuth user) {
        UserAuthEntity entity = new UserAuthEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPasswordHash(user.getPasswordHash());
        entity.setStatus(UserAuthEntity.UserStatusJpa.valueOf(user.getStatus().name()));
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());
        entity.setDeletedAt(user.getDeletedAt());
        entity.setVersion(user.getVersion() != null ? user.getVersion() : 0);
        entity.setDeleted(user.isDeleted());
        return entity;
    }
}