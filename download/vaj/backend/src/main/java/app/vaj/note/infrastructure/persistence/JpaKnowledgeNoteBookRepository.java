package app.vaj.note.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaKnowledgeNoteBookRepository extends JpaRepository<KnowledgeNoteBookEntity, KnowledgeNoteBookEntity.NoteBookId> {
    List<KnowledgeNoteBookEntity> findByNoteId(UUID noteId);

    List<KnowledgeNoteBookEntity> findByBookId(UUID bookId);

    void deleteByNoteId(UUID noteId);
}