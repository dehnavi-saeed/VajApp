package app.vaj.reading.application.handler;

import app.vaj.reading.application.dto.ReadingSessionResponse;
import app.vaj.reading.domain.ReadingSession;
import app.vaj.reading.domain.repository.ReadingSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ListReadingSessionsHandler {

    private final ReadingSessionRepository repository;

    public ListReadingSessionsHandler(ReadingSessionRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ReadingSessionResponse> handle(UUID bookId) {
        return repository.findByBookId(bookId).stream()
                .map(s -> new ReadingSessionResponse(s.getId(), s.getBookId(), s.getStartPage(),
                        s.getEndPage(), s.getDurationMinutes(), s.getState().name(),
                        s.getCreatedAt(), s.getUpdatedAt()))
                .toList();
    }
}