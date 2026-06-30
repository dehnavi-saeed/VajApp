package app.vaj.goal.application.handler;

import app.vaj.goal.application.command.UpdateGoalCommand;
import app.vaj.goal.application.dto.ReadingGoalResponse;
import app.vaj.goal.domain.ReadingGoal;
import app.vaj.goal.domain.repository.ReadingGoalRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class UpdateGoalHandler {
    private final ReadingGoalRepository repo;
    private final Clock clock;

    public UpdateGoalHandler(ReadingGoalRepository repo, Clock clock) {
        this.repo = repo;
        this.clock = clock;
    }

    @Transactional
    public ReadingGoalResponse handle(UUID id, UpdateGoalCommand cmd) {
        ReadingGoal g = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ReadingGoal", id));
        g.update(
                cmd.target() != null ? cmd.target() : g.getTarget(),
                cmd.startDate() != null ? cmd.startDate() : g.getStartDate(),
                cmd.endDate() != null ? cmd.endDate() : g.getEndDate(),
                clock
        );
        repo.save(g);
        return new ReadingGoalResponse(g.getId(), g.getType().name(), g.getTarget(),
                g.getCurrentProgress(), g.getStartDate(), g.getEndDate(),
                g.getStatus().name(), g.getCreatedAt());
    }
}