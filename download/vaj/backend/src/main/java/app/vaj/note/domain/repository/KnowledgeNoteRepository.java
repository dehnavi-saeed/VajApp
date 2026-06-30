package app.vaj.note.domain.repository;
import app.vaj.note.domain.KnowledgeNote;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface KnowledgeNoteRepository {
    KnowledgeNote save(KnowledgeNote n);
    Optional<KnowledgeNote> findById(UUID id);
    List<KnowledgeNote> findByUserId(UUID userId);
}