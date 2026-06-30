package app.vaj.note.application.handler;
import app.vaj.note.application.command.CreateNoteCommand;
import app.vaj.note.application.dto.NoteResponse;
import app.vaj.note.domain.KnowledgeNote;
import app.vaj.note.domain.repository.KnowledgeNoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;
@Service
public class CreateNoteHandler {
    private final KnowledgeNoteRepository repo; private final Clock clock;
    public CreateNoteHandler(KnowledgeNoteRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public NoteResponse handle(UUID userId, CreateNoteCommand cmd) {
        KnowledgeNote n = KnowledgeNote.create(UUID.randomUUID(), userId, cmd.title(), cmd.content(), clock);
        repo.save(n);
        return new NoteResponse(n.getId(), n.getTitle(), n.getContent(), n.getStatus().name(), n.getCreatedAt(), n.getUpdatedAt());
    }
}