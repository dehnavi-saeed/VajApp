package app.vaj.collection.application.handler;
import app.vaj.collection.domain.Collection;
import app.vaj.collection.domain.repository.CollectionRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;
@Service
public class DeleteCollectionHandler {
    private final CollectionRepository repo; private final Clock clock;
    public DeleteCollectionHandler(CollectionRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public void handle(UUID id) {
        Collection c = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Collection", id));
        c.delete(clock); repo.save(c);
    }
}