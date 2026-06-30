package app.vaj.tag.application.handler;

import app.vaj.tag.domain.Tag;
import app.vaj.tag.domain.repository.TagRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class DeleteTagHandler {
    private final TagRepository repo;
    private final Clock clock;

    public DeleteTagHandler(TagRepository repo, Clock clock) {
        this.repo = repo;
        this.clock = clock;
    }

    @Transactional
    public void handle(UUID id) {
        Tag t = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag", id));
        t.delete(clock);
        repo.save(t);
    }
}