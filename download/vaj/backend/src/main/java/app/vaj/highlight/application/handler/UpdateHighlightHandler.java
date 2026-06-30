package app.vaj.highlight.application.handler;
import app.vaj.highlight.application.command.UpdateHighlightCommand;
import app.vaj.highlight.application.dto.HighlightResponse;
import app.vaj.highlight.domain.*;
import app.vaj.highlight.domain.repository.HighlightRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;
@Service
public class UpdateHighlightHandler {
    private final HighlightRepository repo; private final Clock clock;
    public UpdateHighlightHandler(HighlightRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public HighlightResponse handle(UUID id, UpdateHighlightCommand cmd) {
        Highlight h = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Highlight", id));
        if (cmd.color() != null) h.updateColor(HighlightColor.valueOf(cmd.color()), clock);
        if (cmd.comment() != null) h.updateComment(cmd.comment(), clock);
        repo.save(h);
        return new HighlightResponse(h.getId(), h.getBookId(), h.getPage(), h.getStartPosition(), h.getEndPosition(),
                h.getTextSnapshot(), h.getColor().name(), h.getComment(), h.getStatus().name(), h.getCreatedAt(), h.getUpdatedAt());
    }
}