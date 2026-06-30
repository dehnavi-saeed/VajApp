package app.vaj.highlight.application.handler;
import app.vaj.highlight.application.command.CreateHighlightCommand;
import app.vaj.highlight.application.dto.HighlightResponse;
import app.vaj.highlight.domain.*;
import app.vaj.highlight.domain.repository.HighlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;
@Service
public class CreateHighlightHandler {
    private final HighlightRepository repo; private final Clock clock;
    public CreateHighlightHandler(HighlightRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public HighlightResponse handle(UUID userId, CreateHighlightCommand cmd) {
        HighlightColor color = cmd.color() != null ? HighlightColor.valueOf(cmd.color()) : HighlightColor.YELLOW;
        Highlight h = Highlight.create(UUID.randomUUID(), cmd.bookId(), userId, cmd.page(),
                cmd.startPosition(), cmd.endPosition(), cmd.textSnapshot(), color, cmd.comment(), clock);
        repo.save(h);
        return new HighlightResponse(h.getId(), h.getBookId(), h.getPage(), h.getStartPosition(), h.getEndPosition(),
                h.getTextSnapshot(), h.getColor().name(), h.getComment(), h.getStatus().name(), h.getCreatedAt(), h.getUpdatedAt());
    }
}