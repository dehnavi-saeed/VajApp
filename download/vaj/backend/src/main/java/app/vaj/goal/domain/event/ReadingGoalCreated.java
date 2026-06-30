package app.vaj.goal.domain.event;
import app.vaj.common.domain.event.DomainEvent;
import java.time.Instant;
import java.util.UUID;
public class ReadingGoalCreated extends DomainEvent {
    private final UUID goalId; private final UUID userId; private final String type;
    public ReadingGoalCreated(UUID eid, Instant at, UUID goalId, UUID userId, String type) { super(eid, at); this.goalId = goalId; this.userId = userId; this.type = type; }
    public UUID getGoalId() { return goalId; }
    public UUID getUserId() { return userId; }
    public String getType() { return type; }
}