package app.vaj.highlight.application.handler;
import app.vaj.highlight.domain.Highlight;
import app.vaj.highlight.domain.repository.HighlightRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;
@Service
public class DeleteHighlightHandler {
    private final HighlightRepository repo; private final Clock clock;
    public DeleteHighlightHandler(HighlightRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public void handle(UUID id) {
        Highlight h = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Highlight", id));
        h.delete(clock); repo.save(h);
    }
}