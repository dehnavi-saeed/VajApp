package app.vaj.readingstate.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaReadingStateRepository extends JpaRepository<ReadingStateEntity, UUID> {

    Optional<ReadingStateEntity> findByBookId(UUID bookId);

    java.util.List<ReadingStateEntity> findByUserIdOrderByLastReadAtDesc(UUID userId);
}