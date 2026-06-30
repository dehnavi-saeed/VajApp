package app.vaj.tag.application.handler;

import app.vaj.tag.application.command.UpdateTagCommand;
import app.vaj.tag.application.dto.TagResponse;
import app.vaj.tag.domain.Tag;
import app.vaj.tag.domain.repository.TagRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class UpdateTagHandler {
    private final TagRepository repo;
    private final Clock clock;

    public UpdateTagHandler(TagRepository repo, Clock clock) {
        this.repo = repo;
        this.clock = clock;
    }

    @Transactional
    public TagResponse handle(UUID id, UpdateTagCommand cmd) {
        Tag t = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag", id));
        t.update(cmd.name(), cmd.color(), cmd.description(), clock);
        repo.save(t);
        return new TagResponse(t.getId(), t.getName(), t.getColor(), t.getDescription(), t.getStatus().name(), t.getCreatedAt());
    }
}