package app.vaj.highlight.infrastructure.persistence;
import app.vaj.highlight.domain.*;
import app.vaj.highlight.domain.repository.HighlightRepository;
import org.springframework.stereotype.Repository;
import java.lang.reflect.Field;
import java.util.*;
@Repository
public class HighlightRepositoryImpl implements HighlightRepository {
    private final JpaHighlightRepository jpa;
    public HighlightRepositoryImpl(JpaHighlightRepository jpa) { this.jpa = jpa; }
    @Override public Highlight save(Highlight h) { jpa.save(toEntity(h)); return h; }
    @Override public Optional<Highlight> findById(UUID id) { return jpa.findByIdAndIsDeletedFalse(id).map(this::toDomain); }
    @Override public List<Highlight> findByBookId(UUID bookId) { return jpa.findByBookIdAndIsDeletedFalseOrderByPageAsc(bookId).stream().map(this::toDomain).toList(); }

    private Highlight toDomain(HighlightEntity e) {
        try {
            var c = Highlight.class.getDeclaredConstructor(UUID.class); c.setAccessible(true);
            Highlight h = c.newInstance(e.getId());
            set(h,"bookId",e.getBookId()); set(h,"userId",e.getUserId()); set(h,"page",e.getPage());
            set(h,"startPosition",e.getStartPosition()); set(h,"endPosition",e.getEndPosition());
            set(h,"textSnapshot",e.getTextSnapshot()); set(h,"color",HighlightColor.valueOf(e.getColor()));
            set(h,"comment",e.getComment()); set(h,"status",HighlightStatus.valueOf(e.getStatus()));
            set(h,"createdAt",e.getCreatedAt()); set(h,"updatedAt",e.getUpdatedAt());
            return h;
        } catch (Exception ex) { throw new RuntimeException("Mapping failed", ex); }
    }
    private HighlightEntity toEntity(Highlight h) {
        HighlightEntity e = new HighlightEntity();
        e.setId(h.getId()); e.setBookId(h.getBookId()); e.setUserId(h.getUserId()); e.setPage(h.getPage());
        e.setStartPosition(h.getStartPosition()); e.setEndPosition(h.getEndPosition());
        e.setTextSnapshot(h.getTextSnapshot()); e.setColor(h.getColor().name());
        e.setComment(h.getComment()); e.setStatus(h.getStatus().name());
        e.setCreatedAt(h.getCreatedAt()!=null?h.getCreatedAt():java.time.Instant.now());
        e.setUpdatedAt(h.getUpdatedAt()!=null?h.getUpdatedAt():java.time.Instant.now());
        e.setCreatedBy(h.getUserId()); e.setVersion(h.getVersion()!=null?h.getVersion():0L); e.setDeleted(h.isDeleted());
        return e;
    }
    private void set(Object t,String n,Object v) {
        try { Field f=t.getClass().getDeclaredField(n); f.setAccessible(true); f.set(t,v); }
        catch(Exception e) { try { Field f=t.getClass().getSuperclass().getDeclaredField(n); f.setAccessible(true); f.set(t,v); } catch(Exception ignored) {} }
    }
}