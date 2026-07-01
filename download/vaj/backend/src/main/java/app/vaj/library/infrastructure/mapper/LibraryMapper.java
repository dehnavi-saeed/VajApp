package app.vaj.library.infrastructure.mapper;

import app.vaj.library.application.dto.LibraryDetailResponse;
import app.vaj.library.application.dto.LibraryResponse;
import app.vaj.library.domain.Library;
import app.vaj.library.domain.LibraryStatus;
import app.vaj.library.infrastructure.persistence.LibraryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ObjectFactory;

import java.lang.reflect.Field;

@Mapper(componentModel = "spring")
public interface LibraryMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    LibraryResponse toResponse(Library library);

    @Mapping(target = "status", source = "library.status", qualifiedByName = "statusToString")
    @Mapping(target = "bookCount", source = "bookCount")
    LibraryDetailResponse toDetailResponse(Library library, long bookCount);

    @Mapping(target = "status", source = "status", qualifiedByName = "statusToJpaEnum")
    @Mapping(target = "deleted", source = ".", qualifiedByName = "isDeletedCheck")
    LibraryEntity toEntity(Library library);

    @ObjectFactory
    default Library createLibrary(LibraryEntity entity) {
        try {
            var constructor = Library.class.getDeclaredConstructor(java.util.UUID.class);
            constructor.setAccessible(true);
            return constructor.newInstance(entity.getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Library instance", e);
        }
    }

    default Library toDomain(LibraryEntity entity) {
        Library library = createLibrary(entity);
        try {
            setField(library, "userId", entity.getUserId());
            setField(library, "name", entity.getName());
            setField(library, "description", entity.getDescription());
            setField(library, "status", entity.getStatus() != null
                    ? LibraryStatus.valueOf(entity.getStatus().name()) : LibraryStatus.ACTIVE);
            setField(library, "createdAt", entity.getCreatedAt());
            setField(library, "updatedAt", entity.getUpdatedAt());
            setField(library, "version", entity.getVersion());
            if (entity.getDeletedAt() != null) {
                setField(library, "deletedAt", entity.getDeletedAt());
                setField(library, "deletedBy", entity.getDeletedBy());
            }
            setField(library, "createdBy", entity.getCreatedBy());
            setField(library, "updatedBy", entity.getUpdatedBy());
        } catch (Exception e) {
            throw new RuntimeException("Failed to reconstruct Library from entity", e);
        }
        return library;
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = findField(target.getClass(), fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private static Field findField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Class<?> current = clazz;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    @Named("statusToString")
    static String statusToString(LibraryStatus status) {
        return status != null ? status.name() : null;
    }

    @Named("statusToJpaEnum")
    static LibraryEntity.LibraryStatusJpa statusToJpaEnum(LibraryStatus status) {
        return status != null ? LibraryEntity.LibraryStatusJpa.valueOf(status.name()) : null;
    }

    @Named("isDeletedCheck")
    static boolean isDeletedCheck(Library library) {
        return library.isDeleted();
    }
}