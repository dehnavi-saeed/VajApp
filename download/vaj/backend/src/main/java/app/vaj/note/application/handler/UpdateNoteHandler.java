package app.vaj.note.application.handler;
import app.vaj.note.application.command.UpdateNoteCommand;
import app.vaj.note.application.dto.NoteResponse;
import app.vaj.note.domain.KnowledgeNote;
import app.vaj.note.domain.repository.KnowledgeNoteRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;
@Service
public class UpdateNoteHandler {
    private final KnowledgeNoteRepository repo; private final Clock clock;
    public UpdateNoteHandler(KnowledgeNoteRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public NoteResponse handle(UUID id, UpdateNoteCommand cmd) {
        KnowledgeNote n = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("KnowledgeNote", id));
        n.update(cmd.title(), cmd.content(), clock); repo.save(n);
        return new NoteResponse(n.getId(), n.getTitle(), n.getContent(), n.getStatus().name(), n.getCreatedAt(), n.getUpdatedAt());
    }
}