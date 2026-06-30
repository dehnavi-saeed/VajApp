package app.vaj.reading.infrastructure.persistence;

import app.vaj.reading.domain.ReadingSession;
import app.vaj.reading.domain.ReadingState;
import app.vaj.reading.domain.repository.ReadingSessionRepository;
import org.springframework.stereotype.Repository;
import java.lang.reflect.Field;
import java.util.*;

@Repository
public class ReadingSessionRepositoryImpl implements ReadingSessionRepository {

    private final JpaReadingSessionRepository jpa;

    public ReadingSessionRepositoryImpl(JpaReadingSessionRepository jpa) { this.jpa = jpa; }

    @Override public ReadingSession save(ReadingSession s) { jpa.save(toEntity(s)); return s; }

    @Override public Optional<ReadingSession> findById(UUID id) {
        return jpa.findByIdAndIsDeletedFalse(id).map(this::toDomain);
    }

    @Override public List<ReadingSession> findByBookId(UUID bookId) {
        return jpa.findByBookIdAndIsDeletedFalseOrderByCreatedAtDesc(bookId).stream().map(this::toDomain).toList();
    }

    @Override public Optional<ReadingSession> findActiveByBookId(UUID bookId) {
        return jpa.findByBookIdAndStateAndIsDeletedFalse(bookId, ReadingSessionEntity.StateJpa.STARTED)
                .map(this::toDomain);
    }

    @Override public boolean hasActiveSession(UUID bookId) {
        return jpa.existsByBookIdAndStateAndIsDeletedFalse(bookId, ReadingSessionEntity.StateJpa.STARTED);
    }

    private ReadingSession toDomain(ReadingSessionEntity e) {
        try {
            var c = ReadingSession.class.getDeclaredConstructor(UUID.class);
            c.setAccessible(true);
            ReadingSession s = c.newInstance(e.getId());
            set(s, "bookId", e.getBookId());
            set(s, "userId", e.getUserId());
            set(s, "startPage", e.getStartPage());
            set(s, "endPage", e.getEndPage());
            set(s, "durationMinutes", e.getDurationMinutes());
            set(s, "state", ReadingState.valueOf(e.getState().name()));
            set(s, "createdAt", e.getCreatedAt());
            set(s, "updatedAt", e.getUpdatedAt());
            return s;
        } catch (Exception ex) { throw new RuntimeException("Mapping failed", ex); }
    }

    private ReadingSessionEntity toEntity(ReadingSession s) {
        ReadingSessionEntity e = new ReadingSessionEntity();
        e.setId(s.getId()); e.setBookId(s.getBookId()); e.setUserId(s.getUserId());
        e.setStartPage(s.getStartPage()); e.setEndPage(s.getEndPage());
        e.setDurationMinutes(s.getDurationMinutes());
        e.setState(ReadingSessionEntity.StateJpa.valueOf(s.getState().name()));
        e.setCreatedAt(s.getCreatedAt() != null ? s.getCreatedAt() : java.time.Instant.now());
        e.setUpdatedAt(s.getUpdatedAt() != null ? s.getUpdatedAt() : java.time.Instant.now());
        e.setCreatedBy(s.getUserId()); e.setVersion(s.getVersion() != null ? s.getVersion() : 0L);
        e.setDeleted(s.isDeleted());
        return e;
    }

    private void set(Object t, String n, Object v) {
        try {
            Field f = t.getClass().getDeclaredField(n); f.setAccessible(true); f.set(t, v);
        } catch (Exception e) {
            try { Field f = t.getClass().getSuperclass().getDeclaredField(n); f.setAccessible(true); f.set(t, v); }
            catch (Exception ignored) {}
        }
    }
}