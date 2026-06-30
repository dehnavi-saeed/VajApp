package app.vaj.note.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaKnowledgeNoteHighlightRepository extends JpaRepository<KnowledgeNoteHighlightEntity, KnowledgeNoteHighlightEntity.NoteHighlightId> {
    List<KnowledgeNoteHighlightEntity> findByNoteId(UUID noteId);

    void deleteByNoteId(UUID noteId);
}