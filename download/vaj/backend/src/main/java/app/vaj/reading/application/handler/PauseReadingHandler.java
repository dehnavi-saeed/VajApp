package app.vaj.reading.application.handler;

import app.vaj.reading.application.command.PauseReadingCommand;
import app.vaj.reading.application.dto.ReadingSessionResponse;
import app.vaj.reading.domain.ReadingSession;
import app.vaj.reading.domain.repository.ReadingSessionRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class PauseReadingHandler {

    private final ReadingSessionRepository repository;
    private final Clock clock;

    public PauseReadingHandler(ReadingSessionRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    @Transactional
    public ReadingSessionResponse handle(UUID sessionId, PauseReadingCommand command) {
        ReadingSession session = repository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("ReadingSession", sessionId));
        session.pause(command.endPage(), command.durationMinutes(), clock);
        repository.save(session);
        return new ReadingSessionResponse(session.getId(), session.getBookId(), session.getStartPage(),
                session.getEndPage(), session.getDurationMinutes(), session.getState().name(),
                session.getCreatedAt(), session.getUpdatedAt());
    }
}