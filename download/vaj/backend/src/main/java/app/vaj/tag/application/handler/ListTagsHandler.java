package app.vaj.tag.application.handler;
import app.vaj.tag.application.dto.TagResponse;
import app.vaj.tag.domain.Tag;
import app.vaj.tag.domain.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
@Service
public class ListTagsHandler {
    private final TagRepository repo;
    public ListTagsHandler(TagRepository repo) { this.repo = repo; }
    @Transactional(readOnly = true)
    public List<TagResponse> handle(UUID userId) {
        return repo.findByUserId(userId).stream()
                .map(t -> new TagResponse(t.getId(), t.getName(), t.getColor(), t.getDescription(), t.getStatus().name(), t.getCreatedAt()))
                .toList();
    }
}