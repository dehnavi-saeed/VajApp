package app.vaj.collection.infrastructure.persistence;
import app.vaj.collection.domain.*;
import app.vaj.collection.domain.repository.CollectionRepository;
import org.springframework.stereotype.Repository;
import java.lang.reflect.Field;
import java.util.*;
@Repository
public class CollectionRepositoryImpl implements CollectionRepository {
    private final JpaCollectionRepository jpa;
    public CollectionRepositoryImpl(JpaCollectionRepository jpa) { this.jpa = jpa; }
    @Override public Collection save(Collection c) { jpa.save(toEntity(c)); return c; }
    @Override public Optional<Collection> findById(UUID id) { return jpa.findByIdAndIsDeletedFalse(id).map(this::toDomain); }
    @Override public List<Collection> findByUserId(UUID userId) { return jpa.findByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(userId).stream().map(this::toDomain).toList(); }
    private Collection toDomain(CollectionEntity e) {
        try {
            var c = Collection.class.getDeclaredConstructor(UUID.class); c.setAccessible(true);
            Collection col = c.newInstance(e.getId());
            set(col,"userId",e.getUserId()); set(col,"name",e.getName()); set(col,"description",e.getDescription());
            set(col,"status",CollectionStatus.valueOf(e.getStatus())); set(col,"createdAt",e.getCreatedAt()); set(col,"updatedAt",e.getUpdatedAt());
            return col;
        } catch (Exception ex) { throw new RuntimeException("Mapping failed", ex); }
    }
    private CollectionEntity toEntity(Collection c) {
        CollectionEntity e = new CollectionEntity();
        e.setId(c.getId()); e.setUserId(c.getUserId()); e.setName(c.getName()); e.setDescription(c.getDescription());
        e.setStatus(c.getStatus().name());
        e.setCreatedAt(c.getCreatedAt()!=null?c.getCreatedAt():java.time.Instant.now());
        e.setUpdatedAt(c.getUpdatedAt()!=null?c.getUpdatedAt():java.time.Instant.now());
        e.setCreatedBy(c.getUserId()); e.setVersion(c.getVersion()!=null?c.getVersion():0L); e.setDeleted(c.isDeleted());
        return e;
    }
    private void set(Object t,String n,Object v) {
        try { Field f=t.getClass().getDeclaredField(n); f.setAccessible(true); f.set(t,v); }
        catch(Exception e) { try { Field f=t.getClass().getSuperclass().getDeclaredField(n); f.setAccessible(true); f.set(t,v); } catch(Exception ignored) {} }
    }
}