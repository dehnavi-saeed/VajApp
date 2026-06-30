package app.vaj.goal.domain.repository;
import app.vaj.goal.domain.ReadingGoal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface ReadingGoalRepository {
    ReadingGoal save(ReadingGoal goal);
    Optional<ReadingGoal> findById(UUID id);
    List<ReadingGoal> findByUserId(UUID userId);
}