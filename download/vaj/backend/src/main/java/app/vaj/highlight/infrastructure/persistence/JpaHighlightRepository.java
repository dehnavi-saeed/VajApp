package app.vaj.highlight.infrastructure.persistence;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import java.util.UUID;
public interface JpaHighlightRepository extends JpaRepository<HighlightEntity, UUID> {
    Optional<HighlightEntity> findByIdAndIsDeletedFalse(UUID id);
    List<HighlightEntity> findByBookIdAndIsDeletedFalseOrderByPageAsc(UUID bookId);
    long countByUserIdAndIsDeletedFalse(UUID userId);
    List<HighlightEntity> findByUserIdAndTextSnapshotContainingIgnoreCaseAndIsDeletedFalse(UUID userId, String text, Pageable pageable);
}