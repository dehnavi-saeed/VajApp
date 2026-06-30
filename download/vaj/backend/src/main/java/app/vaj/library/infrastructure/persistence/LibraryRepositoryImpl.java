package app.vaj.library.infrastructure.persistence;

import app.vaj.library.domain.Library;
import app.vaj.library.domain.LibraryStatus;
import app.vaj.library.domain.repository.LibraryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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
        Library lib = new Library(e.getId());
        lib = reconstruct(lib, e);
        return lib;
    }

    private Library reconstruct(Library lib, LibraryEntity e) {
        try {
            set(lib, "userId", e.getUserId());
            set(lib, "name", e.getName());
            set(lib, "description", e.getDescription());
            set(lib, "status", e.getStatus() != null ? LibraryStatus.valueOf(e.getStatus().name()) : LibraryStatus.ACTIVE);
            set(lib, "createdAt", e.getCreatedAt());
            set(lib, "updatedAt", e.getUpdatedAt());
            set(lib, "version", e.getVersion());
        } catch (Exception ex) {
            throw new RuntimeException("Failed to reconstruct Library", ex);
        }
        return lib;
    }

    private void set(Object target, String field, Object value) throws Exception {
        java.lang.reflect.Field f = target.getClass().getDeclaredField(field);
        f.setAccessible(true);
        f.set(target, value);
    }

    private LibraryEntity toEntity(Library lib) {
        LibraryEntity e = new LibraryEntity();
        e.setId(lib.getId());
        e.setUserId(lib.getUserId());
        e.setName(lib.getName());
        e.setDescription(lib.getDescription());
        e.setStatus(LibraryEntity.LibraryStatusJpa.valueOf(lib.getStatus().name()));
        e.setCreatedAt(lib.getCreatedAt() != null ? lib.getCreatedAt() : java.time.Instant.now());
        e.setUpdatedAt(lib.getUpdatedAt() != null ? lib.getUpdatedAt() : java.time.Instant.now());
        e.setDeleted(lib.isDeleted());
        e.setVersion(lib.getVersion() != null ? lib.getVersion() : 0);
        return e;
    }
}