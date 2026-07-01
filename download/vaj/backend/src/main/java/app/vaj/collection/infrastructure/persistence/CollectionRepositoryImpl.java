package app.vaj.collection.infrastructure.persistence;

import app.vaj.collection.domain.Collection;
import app.vaj.collection.domain.CollectionStatus;
import app.vaj.collection.domain.repository.CollectionRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CollectionRepositoryImpl implements CollectionRepository {
    private final JpaCollectionRepository jpa;

    public CollectionRepositoryImpl(JpaCollectionRepository jpa) { this.jpa = jpa; }

    @Override
    public Collection save(Collection c) { jpa.save(toEntity(c)); return c; }

    @Override
    public Optional<Collection> findById(UUID id) { return jpa.findByIdAndIsDeletedFalse(id).map(this::toDomain); }

    @Override
    public List<Collection> findByUserId(UUID userId) {
        return jpa.findByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(userId).stream().map(this::toDomain).toList();
    }

    private Collection toDomain(CollectionEntity e) {
        try {
            var ctor = Collection.class.getDeclaredConstructor(UUID.class);
            ctor.setAccessible(true);
            Collection col = ctor.newInstance(e.getId());
            setField(col, "userId", e.getUserId());
            setField(col, "name", e.getName());
            setField(col, "description", e.getDescription());
            setField(col, "status", CollectionStatus.valueOf(e.getStatus()));
            setField(col, "createdAt", e.getCreatedAt());
            setField(col, "updatedAt", e.getUpdatedAt());
            return col;
        } catch (Exception ex) { throw new RuntimeException("Mapping failed", ex); }
    }

    private CollectionEntity toEntity(Collection c) {
        CollectionEntity e = new CollectionEntity();
        e.setId(c.getId());
        e.setUserId(c.getUserId());
        e.setName(c.getName());
        e.setDescription(c.getDescription());
        e.setStatus(c.getStatus().name());
        e.setCreatedAt(c.getCreatedAt() != null ? c.getCreatedAt() : Instant.now());
        e.setUpdatedAt(c.getUpdatedAt() != null ? c.getUpdatedAt() : Instant.now());
        e.setCreatedBy(c.getUserId());
        e.setVersion(c.getVersion() != null ? c.getVersion() : 0L);
        e.setDeleted(c.isDeleted());
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