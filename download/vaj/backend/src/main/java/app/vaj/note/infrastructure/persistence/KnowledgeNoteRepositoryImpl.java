package app.vaj.note.infrastructure.persistence;
import app.vaj.note.domain.*;
import app.vaj.note.domain.repository.KnowledgeNoteRepository;
import org.springframework.stereotype.Repository;
import java.lang.reflect.Field;
import java.util.*;
@Repository
public class KnowledgeNoteRepositoryImpl implements KnowledgeNoteRepository {
    private final JpaKnowledgeNoteRepository jpa;
    public KnowledgeNoteRepositoryImpl(JpaKnowledgeNoteRepository jpa) { this.jpa = jpa; }
    @Override public KnowledgeNote save(KnowledgeNote n) { jpa.save(toEntity(n)); return n; }
    @Override public Optional<KnowledgeNote> findById(UUID id) { return jpa.findByIdAndIsDeletedFalse(id).map(this::toDomain); }
    @Override public List<KnowledgeNote> findByUserId(UUID userId) { return jpa.findByUserIdAndIsDeletedFalseOrderByUpdatedAtDesc(userId).stream().map(this::toDomain).toList(); }

    private KnowledgeNote toDomain(KnowledgeNoteEntity e) {
        try {
            var c = KnowledgeNote.class.getDeclaredConstructor(UUID.class); c.setAccessible(true);
            KnowledgeNote n = c.newInstance(e.getId());
            set(n,"userId",e.getUserId()); set(n,"title",e.getTitle()); set(n,"content",e.getContent());
            set(n,"status",NoteStatus.valueOf(e.getStatus())); set(n,"createdAt",e.getCreatedAt()); set(n,"updatedAt",e.getUpdatedAt());
            return n;
        } catch (Exception ex) { throw new RuntimeException("Mapping failed", ex); }
    }
    private KnowledgeNoteEntity toEntity(KnowledgeNote n) {
        KnowledgeNoteEntity e = new KnowledgeNoteEntity();
        e.setId(n.getId()); e.setUserId(n.getUserId()); e.setTitle(n.getTitle()); e.setContent(n.getContent());
        e.setStatus(n.getStatus().name());
        e.setCreatedAt(n.getCreatedAt()!=null?n.getCreatedAt():java.time.Instant.now());
        e.setUpdatedAt(n.getUpdatedAt()!=null?n.getUpdatedAt():java.time.Instant.now());
        e.setCreatedBy(n.getUserId()); e.setVersion(n.getVersion()!=null?n.getVersion():0L); e.setDeleted(n.isDeleted());
        return e;
    }
    private void set(Object t,String n,Object v) {
        try { Field f=t.getClass().getDeclaredField(n); f.setAccessible(true); f.set(t,v); }
        catch(Exception e) { try { Field f=t.getClass().getSuperclass().getDeclaredField(n); f.setAccessible(true); f.set(t,v); } catch(Exception ignored) {} }
    }
}