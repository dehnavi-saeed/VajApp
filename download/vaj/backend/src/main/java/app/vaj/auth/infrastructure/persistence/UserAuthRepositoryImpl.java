package app.vaj.auth.infrastructure.persistence;

import app.vaj.auth.domain.UserAuth;
import app.vaj.auth.domain.UserAuthStatus;
import app.vaj.auth.domain.repository.UserAuthRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
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
        try {
            var ctor = UserAuth.class.getDeclaredConstructor(UUID.class);
            ctor.setAccessible(true);
            UserAuth user = ctor.newInstance(entity.getId());
            setField(user, "username", entity.getUsername());
            setField(user, "email", entity.getEmail());
            setField(user, "passwordHash", entity.getPasswordHash());
            setField(user, "status", entity.getStatus() != null ? UserAuthStatus.valueOf(entity.getStatus().name()) : UserAuthStatus.PENDING);
            setField(user, "createdAt", entity.getCreatedAt());
            setField(user, "updatedAt", entity.getUpdatedAt());
            setField(user, "deletedAt", entity.getDeletedAt());
            setField(user, "version", entity.getVersion());
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Failed to reconstruct UserAuth", e);
        }
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
        entity.setVersion(user.getVersion() != null ? user.getVersion() : 0L);
        entity.setDeleted(user.isDeleted());
        return entity;
    }

    private void setField(Object target, String name, Object value) {
        try {
            Field f = target.getClass().getDeclaredField(name);
            f.setAccessible(true);
            f.set(target, value);
        } catch (NoSuchFieldException ex) {
            try {
                Field f = target.getClass().getSuperclass().getDeclaredField(name);
                f.setAccessible(true);
                f.set(target, value);
            } catch (Exception ignored) {}
        } catch (IllegalAccessException ignored) {}
    }
}