package app.vaj.library.infrastructure.persistence;

import app.vaj.library.domain.Library;
import app.vaj.library.domain.LibraryStatus;
import app.vaj.library.domain.repository.LibraryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LibraryRepositoryImpl implements LibraryRepository {

    private final JpaLibraryRepository jpa;

    public LibraryRepositoryImpl(JpaLibraryRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Library save(Library library) {
        LibraryEntity entity = toEntity(library);
        jpa.save(entity);
        return library;
    }

    @Override
    public Optional<Library> findById(UUID id) {
        return jpa.findByIdAndIsDeletedFalse(id).map(this::toDomain);
    }

    @Override
    public Page<Library> findByUserId(UUID userId, Pageable pageable) {
        return jpa.findByUserIdAndIsDeletedFalse(userId, pageable).map(this::toDomain);
    }

    @Override
    public boolean existsByUserIdAndName(UUID userId, String name) {
        return jpa.existsByUserIdAndNameAndIsDeletedFalse(userId, name);
    }

    @Override
    public long countByUserId(UUID userId) {
        return jpa.countByUserIdAndIsDeletedFalse(userId);
    }

    private Library toDomain(LibraryEntity e) {
        try {
            var ctor = Library.class.getDeclaredConstructor(UUID.class);
            ctor.setAccessible(true);
            Library lib = ctor.newInstance(e.getId());
            setField(lib, "userId", e.getUserId());
            setField(lib, "name", e.getName());
            setField(lib, "description", e.getDescription());
            setField(lib, "status", e.getStatus() != null ? LibraryStatus.valueOf(e.getStatus().name()) : LibraryStatus.ACTIVE);
            setField(lib, "createdAt", e.getCreatedAt());
            setField(lib, "updatedAt", e.getUpdatedAt());
            setField(lib, "version", e.getVersion());
            return lib;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to reconstruct Library", ex);
        }
    }

    private LibraryEntity toEntity(Library lib) {
        LibraryEntity e = new LibraryEntity();
        e.setId(lib.getId());
        e.setUserId(lib.getUserId());
        e.setName(lib.getName());
        e.setDescription(lib.getDescription());
        e.setStatus(LibraryEntity.LibraryStatusJpa.valueOf(lib.getStatus().name()));
        e.setCreatedAt(lib.getCreatedAt() != null ? lib.getCreatedAt() : Instant.now());
        e.setUpdatedAt(lib.getUpdatedAt() != null ? lib.getUpdatedAt() : Instant.now());
        e.setDeleted(lib.isDeleted());
        e.setVersion(lib.getVersion() != null ? lib.getVersion() : 0L);
        return e;
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