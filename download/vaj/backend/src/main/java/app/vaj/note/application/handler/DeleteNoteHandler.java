package app.vaj.note.application.handler;
import app.vaj.note.domain.KnowledgeNote;
import app.vaj.note.domain.repository.KnowledgeNoteRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;
@Service
public class DeleteNoteHandler {
    private final KnowledgeNoteRepository repo; private final Clock clock;
    public DeleteNoteHandler(KnowledgeNoteRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public void handle(UUID id) {
        KnowledgeNote n = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("KnowledgeNote", id));
        n.delete(clock); repo.save(n);
    }
}