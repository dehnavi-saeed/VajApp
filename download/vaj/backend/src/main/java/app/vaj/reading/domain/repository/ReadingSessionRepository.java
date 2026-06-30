package app.vaj.reading.domain.repository;

import app.vaj.reading.domain.ReadingSession;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadingSessionRepository {
    ReadingSession save(ReadingSession session);
    Optional<ReadingSession> findById(UUID id);
    List<ReadingSession> findByBookId(UUID bookId);
    Optional<ReadingSession> findActiveByBookId(UUID bookId);
    boolean hasActiveSession(UUID bookId);
}