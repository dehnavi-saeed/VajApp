package app.vaj.goal.infrastructure.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import java.util.UUID;
public interface JpaReadingGoalRepository extends JpaRepository<ReadingGoalEntity, UUID> {
    Optional<ReadingGoalEntity> findByIdAndIsDeletedFalse(UUID id);
    List<ReadingGoalEntity> findByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(UUID userId);
}