package app.vaj.goal.application.handler;
import app.vaj.goal.application.dto.ReadingGoalResponse;
import app.vaj.goal.domain.ReadingGoal;
import app.vaj.goal.domain.repository.ReadingGoalRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;
@Service
public class ResumeGoalHandler {
    private final ReadingGoalRepository repo; private final Clock clock;
    public ResumeGoalHandler(ReadingGoalRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public ReadingGoalResponse handle(UUID id) {
        ReadingGoal g = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("ReadingGoal", id));
        g.resume(clock); repo.save(g);
        return new ReadingGoalResponse(g.getId(), g.getType().name(), g.getTarget(), g.getCurrentProgress(),
                g.getStartDate(), g.getEndDate(), g.getStatus().name(), g.getCreatedAt());
    }
}