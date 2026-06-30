package app.vaj.collection.application.handler;
import app.vaj.collection.application.command.CreateCollectionCommand;
import app.vaj.collection.application.dto.CollectionResponse;
import app.vaj.collection.domain.Collection;
import app.vaj.collection.domain.repository.CollectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;
@Service
public class CreateCollectionHandler {
    private final CollectionRepository repo; private final Clock clock;
    public CreateCollectionHandler(CollectionRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public CollectionResponse handle(UUID userId, CreateCollectionCommand cmd) {
        Collection c = Collection.create(UUID.randomUUID(), userId, cmd.name(), cmd.description(), clock);
        repo.save(c);
        return new CollectionResponse(c.getId(), c.getName(), c.getDescription(), c.getStatus().name(), c.getCreatedAt());
    }
}