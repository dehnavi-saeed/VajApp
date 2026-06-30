package app.vaj.goal.application.handler;
import app.vaj.goal.domain.ReadingGoal;
import app.vaj.goal.domain.repository.ReadingGoalRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;
@Service
public class CancelGoalHandler {
    private final ReadingGoalRepository repo; private final Clock clock;
    public CancelGoalHandler(ReadingGoalRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public void handle(UUID id) {
        ReadingGoal g = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("ReadingGoal", id));
        g.cancel(clock); repo.save(g);
    }
}