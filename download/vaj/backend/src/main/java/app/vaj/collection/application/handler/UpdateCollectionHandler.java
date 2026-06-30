package app.vaj.collection.application.handler;

import app.vaj.collection.application.command.UpdateCollectionCommand;
import app.vaj.collection.application.dto.CollectionResponse;
import app.vaj.collection.domain.Collection;
import app.vaj.collection.domain.repository.CollectionRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class UpdateCollectionHandler {
    private final CollectionRepository repo;
    private final Clock clock;

    public UpdateCollectionHandler(CollectionRepository repo, Clock clock) {
        this.repo = repo;
        this.clock = clock;
    }

    @Transactional
    public CollectionResponse handle(UUID id, UpdateCollectionCommand cmd) {
        Collection c = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Collection", id));
        c.update(cmd.name(), cmd.description(), clock);
        repo.save(c);
        return new CollectionResponse(c.getId(), c.getName(), c.getDescription(), c.getStatus().name(), c.getCreatedAt());
    }
}