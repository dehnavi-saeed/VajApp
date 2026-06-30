package app.vaj.note.infrastructure.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import java.util.UUID;
public interface JpaKnowledgeNoteRepository extends JpaRepository<KnowledgeNoteEntity, UUID> {
    Optional<KnowledgeNoteEntity> findByIdAndIsDeletedFalse(UUID id);
    List<KnowledgeNoteEntity> findByUserIdAndIsDeletedFalseOrderByUpdatedAtDesc(UUID userId);
}