package app.vaj.highlight.domain.repository;
import app.vaj.highlight.domain.Highlight;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface HighlightRepository {
    Highlight save(Highlight h);
    Optional<Highlight> findById(UUID id);
    List<Highlight> findByBookId(UUID bookId);
}