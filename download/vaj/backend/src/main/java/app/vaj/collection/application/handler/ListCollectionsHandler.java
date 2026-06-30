package app.vaj.collection.application.handler;
import app.vaj.collection.application.dto.CollectionResponse;
import app.vaj.collection.domain.Collection;
import app.vaj.collection.domain.repository.CollectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
@Service
public class ListCollectionsHandler {
    private final CollectionRepository repo;
    public ListCollectionsHandler(CollectionRepository repo) { this.repo = repo; }
    @Transactional(readOnly = true)
    public List<CollectionResponse> handle(UUID userId) {
        return repo.findByUserId(userId).stream()
                .map(c -> new CollectionResponse(c.getId(), c.getName(), c.getDescription(), c.getStatus().name(), c.getCreatedAt()))
                .toList();
    }
}