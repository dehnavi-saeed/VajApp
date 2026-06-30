package app.vaj.note.application.handler;
import app.vaj.note.application.dto.NoteResponse;
import app.vaj.note.domain.KnowledgeNote;
import app.vaj.note.domain.repository.KnowledgeNoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
@Service
public class ListNotesHandler {
    private final KnowledgeNoteRepository repo;
    public ListNotesHandler(KnowledgeNoteRepository repo) { this.repo = repo; }
    @Transactional(readOnly = true)
    public List<NoteResponse> handle(UUID userId) {
        return repo.findByUserId(userId).stream()
                .map(n -> new NoteResponse(n.getId(), n.getTitle(), n.getContent(), n.getStatus().name(), n.getCreatedAt(), n.getUpdatedAt()))
                .toList();
    }
}