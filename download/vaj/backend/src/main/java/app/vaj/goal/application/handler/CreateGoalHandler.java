package app.vaj.goal.application.handler;
import app.vaj.goal.application.command.CreateGoalCommand;
import app.vaj.goal.application.dto.ReadingGoalResponse;
import app.vaj.goal.domain.*;
import app.vaj.goal.domain.event.ReadingGoalCreated;
import app.vaj.goal.domain.repository.ReadingGoalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.time.Instant;
import java.util.UUID;
@Service
public class CreateGoalHandler {
    private final ReadingGoalRepository repo; private final Clock clock;
    public CreateGoalHandler(ReadingGoalRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public ReadingGoalResponse handle(UUID userId, CreateGoalCommand cmd) {
        UUID id = UUID.randomUUID();
        GoalType type = GoalType.valueOf(cmd.type());
        ReadingGoal g = ReadingGoal.create(id, userId, type, cmd.target(), cmd.startDate(), cmd.endDate(), clock);
        g.registerEvent(new ReadingGoalCreated(UUID.randomUUID(), Instant.now(clock), id, userId, cmd.type()));
        repo.save(g);
        return new ReadingGoalResponse(g.getId(), g.getType().name(), g.getTarget(), g.getCurrentProgress(),
                g.getStartDate(), g.getEndDate(), g.getStatus().name(), g.getCreatedAt());
    }
}