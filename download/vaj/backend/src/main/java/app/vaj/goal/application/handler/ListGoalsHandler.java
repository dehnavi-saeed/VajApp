package app.vaj.goal.application.handler;
import app.vaj.goal.application.dto.ReadingGoalResponse;
import app.vaj.goal.domain.ReadingGoal;
import app.vaj.goal.domain.repository.ReadingGoalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
@Service
public class ListGoalsHandler {
    private final ReadingGoalRepository repo;
    public ListGoalsHandler(ReadingGoalRepository repo) { this.repo = repo; }
    @Transactional(readOnly = true)
    public List<ReadingGoalResponse> handle(UUID userId) {
        return repo.findByUserId(userId).stream()
                .map(g -> new ReadingGoalResponse(g.getId(), g.getType().name(), g.getTarget(),
                        g.getCurrentProgress(), g.getStartDate(), g.getEndDate(), g.getStatus().name(), g.getCreatedAt()))
                .toList();
    }
}