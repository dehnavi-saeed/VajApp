package app.vaj.highlight.application.handler;
import app.vaj.highlight.application.dto.HighlightResponse;
import app.vaj.highlight.domain.Highlight;
import app.vaj.highlight.domain.repository.HighlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
@Service
public class ListHighlightsHandler {
    private final HighlightRepository repo;
    public ListHighlightsHandler(HighlightRepository repo) { this.repo = repo; }
    @Transactional(readOnly = true)
    public List<HighlightResponse> handle(UUID bookId) {
        return repo.findByBookId(bookId).stream()
                .map(h -> new HighlightResponse(h.getId(), h.getBookId(), h.getPage(), h.getStartPosition(), h.getEndPosition(),
                        h.getTextSnapshot(), h.getColor().name(), h.getComment(), h.getStatus().name(), h.getCreatedAt(), h.getUpdatedAt()))
                .toList();
    }
}