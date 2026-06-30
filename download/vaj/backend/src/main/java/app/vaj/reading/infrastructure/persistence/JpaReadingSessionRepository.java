package app.vaj.reading.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import java.util.UUID;

public interface JpaReadingSessionRepository extends JpaRepository<ReadingSessionEntity, UUID> {
    Optional<ReadingSessionEntity> findByIdAndIsDeletedFalse(UUID id);
    List<ReadingSessionEntity> findByBookIdAndIsDeletedFalseOrderByCreatedAtDesc(UUID bookId);
    Optional<ReadingSessionEntity> findByBookIdAndStateAndIsDeletedFalse(UUID bookId, ReadingSessionEntity.StateJpa state);
    boolean existsByBookIdAndStateAndIsDeletedFalse(UUID bookId, ReadingSessionEntity.StateJpa state);
}