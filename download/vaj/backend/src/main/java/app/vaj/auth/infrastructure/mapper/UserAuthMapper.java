package app.vaj.auth.infrastructure.mapper;

import app.vaj.auth.domain.RefreshToken;
import app.vaj.auth.domain.UserAuth;
import app.vaj.auth.domain.UserAuthStatus;
import app.vaj.auth.infrastructure.persistence.RefreshTokenEntity;
import app.vaj.auth.infrastructure.persistence.UserAuthEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserAuthMapper {

    // ========== Domain -> Entity ==========

    @Mapping(target = "status", source = "status", qualifiedByName = "toStatusJpa")
    UserAuthEntity toEntity(UserAuth userAuth);

    @Named("toStatusJpa")
    default UserAuthEntity.UserStatusJpa toStatusJpa(UserAuthStatus status) {
        return status != null ? UserAuthEntity.UserStatusJpa.valueOf(status.name()) : null;
    }

    /**
     * RefreshToken domain has no getRevokedAt() getter, so a default method
     * with reflection-based field access is required.
     */
    default RefreshTokenEntity toEntity(RefreshToken token) {
        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setId(token.getId());
        entity.setUserId(token.getUserId());
        entity.setToken(token.getToken());
        entity.setExpiresAt(token.getExpiresAt());
        entity.setCreatedAt(token.getCreatedAt());
        entity.setRevokedAt(accessRevokedAt(token));
        return entity;
    }

    // ========== Entity -> Domain ==========

    /**
     * Reconstructs a {@link UserAuth} aggregate from its JPA entity.
     * Reflection is required because the domain constructor is private
     * (DDD-protected factory-method pattern).
     */
    default UserAuth toDomain(UserAuthEntity entity) {
        try {
            var ctor = UserAuth.class.getDeclaredConstructor(UUID.class);
            ctor.setAccessible(true);
            UserAuth user = ctor.newInstance(entity.getId());

            setField(user, "username", entity.getUsername());
            setField(user, "email", entity.getEmail());
            setField(user, "passwordHash", entity.getPasswordHash());
            setField(user, "status",
                    entity.getStatus() != null
                            ? UserAuthStatus.valueOf(entity.getStatus().name())
                            : UserAuthStatus.PENDING);
            setField(user, "createdAt", entity.getCreatedAt());
            setField(user, "updatedAt", entity.getUpdatedAt());
            setField(user, "deletedAt", entity.getDeletedAt());
            setField(user, "createdBy", entity.getCreatedBy());
            setField(user, "updatedBy", entity.getUpdatedBy());
            setField(user, "deletedBy", entity.getDeletedBy());
            setField(user, "version", entity.getVersion());

            return user;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to reconstruct UserAuth from entity", e);
        }
    }

    /**
     * Reconstructs a {@link RefreshToken} aggregate from its JPA entity.
     * Reflection is required because the domain constructor is private.
     */
    default RefreshToken toDomain(RefreshTokenEntity entity) {
        try {
            var ctor = RefreshToken.class.getDeclaredConstructor(UUID.class);
            ctor.setAccessible(true);
            RefreshToken token = ctor.newInstance(entity.getId());

            setField(token, "userId", entity.getUserId());
            setField(token, "token", entity.getToken());
            setField(token, "expiresAt", entity.getExpiresAt());
            setField(token, "revokedAt", entity.getRevokedAt());
            setField(token, "createdAt", entity.getCreatedAt());

            return token;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to reconstruct RefreshToken from entity", e);
        }
    }

    // ========== Reflection Helpers ==========

    private void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(
                    "Failed to set field '" + fieldName + "' on " + target.getClass().getSimpleName(), e);
        }
    }

    private Instant accessRevokedAt(RefreshToken token) {
        try {
            Field field = RefreshToken.class.getDeclaredField("revokedAt");
            field.setAccessible(true);
            return (Instant) field.get(token);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("Failed to access revokedAt on RefreshToken", e);
        }
    }
}