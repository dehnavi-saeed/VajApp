package app.vaj.tag.infrastructure.persistence;
import app.vaj.tag.domain.*;
import app.vaj.tag.domain.repository.TagRepository;
import org.springframework.stereotype.Repository;
import java.lang.reflect.Field;
import java.util.*;
@Repository
public class TagRepositoryImpl implements TagRepository {
    private final JpaTagRepository jpa;
    public TagRepositoryImpl(JpaTagRepository jpa) { this.jpa = jpa; }
    @Override public Tag save(Tag t) { jpa.save(toEntity(t)); return t; }
    @Override public List<Tag> findByUserId(UUID userId) { return jpa.findByUserIdAndIsDeletedFalseOrderByNameAsc(userId).stream().map(this::toDomain).toList(); }
    @Override public boolean existsByUserIdAndName(UUID userId, String name) { return jpa.existsByUserIdAndNameAndIsDeletedFalse(userId, name); }
    private Tag toDomain(TagEntity e) {
        try {
            var c = Tag.class.getDeclaredConstructor(UUID.class); c.setAccessible(true);
            Tag t = c.newInstance(e.getId());
            set(t,"userId",e.getUserId()); set(t,"name",e.getName()); set(t,"color",e.getColor());
            set(t,"description",e.getDescription()); set(t,"status",TagStatus.valueOf(e.getStatus()));
            set(t,"createdAt",e.getCreatedAt()); set(t,"updatedAt",e.getUpdatedAt());
            return t;
        } catch (Exception ex) { throw new RuntimeException("Mapping failed", ex); }
    }
    private TagEntity toEntity(Tag t) {
        TagEntity e = new TagEntity();
        e.setId(t.getId()); e.setUserId(t.getUserId()); e.setName(t.getName()); e.setColor(t.getColor());
        e.setDescription(t.getDescription()); e.setStatus(t.getStatus().name());
        e.setCreatedAt(t.getCreatedAt()!=null?t.getCreatedAt():java.time.Instant.now());
        e.setUpdatedAt(t.getUpdatedAt()!=null?t.getUpdatedAt():java.time.Instant.now());
        e.setCreatedBy(t.getUserId()); e.setVersion(t.getVersion()!=null?t.getVersion():0L); e.setDeleted(t.isDeleted());
        return e;
    }
    private void set(Object t,String n,Object v) {
        try { Field f=t.getClass().getDeclaredField(n); f.setAccessible(true); f.set(t,v); }
        catch(Exception e) { try { Field f=t.getClass().getSuperclass().getDeclaredField(n); f.setAccessible(true); f.set(t,v); } catch(Exception ignored) {} }
    }
}