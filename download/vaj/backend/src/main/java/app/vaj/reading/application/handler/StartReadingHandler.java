package app.vaj.reading.application.handler;

import app.vaj.reading.application.command.StartReadingCommand;
import app.vaj.reading.application.dto.ReadingSessionResponse;
import app.vaj.reading.domain.ReadingSession;
import app.vaj.reading.domain.event.ReadingStarted;
import app.vaj.reading.domain.repository.ReadingSessionRepository;
import app.vaj.common.domain.DomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Service
public class StartReadingHandler {

    private final ReadingSessionRepository repository;
    private final Clock clock;

    public StartReadingHandler(ReadingSessionRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    @Transactional
    public ReadingSessionResponse handle(UUID userId, StartReadingCommand command) {
        if (repository.hasActiveSession(command.bookId())) {
            throw new DomainException("ACTIVE_SESSION_EXISTS", "An active reading session already exists for this book.");
        }
        UUID id = UUID.randomUUID();
        ReadingSession session = ReadingSession.create(id, command.bookId(), userId, command.startPage(), clock);
        session.registerEvent(new ReadingStarted(UUID.randomUUID(), Instant.now(clock), id, command.bookId(), userId));
        repository.save(session);
        return new ReadingSessionResponse(session.getId(), session.getBookId(), session.getStartPage(),
                session.getEndPage(), session.getDurationMinutes(), session.getState().name(),
                session.getCreatedAt(), session.getUpdatedAt());
    }
}