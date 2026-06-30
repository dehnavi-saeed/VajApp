package app.vaj.tag.application.handler;
import app.vaj.tag.application.command.CreateTagCommand;
import app.vaj.tag.application.dto.TagResponse;
import app.vaj.tag.domain.Tag;
import app.vaj.tag.domain.repository.TagRepository;
import app.vaj.common.domain.DomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;
@Service
public class CreateTagHandler {
    private final TagRepository repo; private final Clock clock;
    public CreateTagHandler(TagRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public TagResponse handle(UUID userId, CreateTagCommand cmd) {
        if (repo.existsByUserIdAndName(userId, cmd.name())) throw new DomainException("TAG_EXISTS", "Tag name already exists.");
        Tag t = Tag.create(UUID.randomUUID(), userId, cmd.name(), cmd.color(), cmd.description(), clock);
        repo.save(t);
        return new TagResponse(t.getId(), t.getName(), t.getColor(), t.getDescription(), t.getStatus().name(), t.getCreatedAt());
    }
}