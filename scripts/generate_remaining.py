#!/usr/bin/env python3
"""Generate all remaining feature files for VAJ project."""
import os

BASE = "/home/z/my-project/download/vaj/backend/src/main/java/app/vaj"
SQL_BASE = "/home/z/my-project/download/vaj/backend/src/main/resources/db/migration"

def w(path, content):
    full = os.path.join(BASE, path)
    os.makedirs(os.path.dirname(full), exist_ok=True)
    with open(full, 'w') as f:
        f.write(content)
    print(f"  OK: {path}")

def sql(path, content):
    full = os.path.join(SQL_BASE, path)
    os.makedirs(os.path.dirname(full), exist_ok=True)
    with open(full, 'w') as f:
        f.write(content)
    print(f"  OK: sql/{path}")

# ============================================================
# READING SESSION
# ============================================================
print("=== Reading Session ===")

w("reading/domain/ReadingState.java", """package app.vaj.reading.domain;

public enum ReadingState {
    STARTED, PAUSED, COMPLETED, CANCELLED
}""")

w("reading/domain/ReadingSession.java", """package app.vaj.reading.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReadingSession extends BaseAggregateRoot {

    private UUID bookId;
    private UUID userId;
    private Integer startPage;
    private Integer endPage;
    private Integer durationMinutes;
    private ReadingState state;

    private ReadingSession(UUID id) { super(id); }

    public static ReadingSession create(UUID id, UUID bookId, UUID userId, int startPage, Clock clock) {
        ReadingSession session = new ReadingSession(id);
        session.bookId = bookId;
        session.userId = userId;
        session.startPage = startPage;
        session.durationMinutes = 0;
        session.state = ReadingState.STARTED;
        session.markCreated(Instant.now(clock), userId);
        return session;
    }

    public void pause(int endPage, int durationMinutes, Clock clock) {
        validateStateTransition(ReadingState.PAUSED);
        if (endPage < startPage) {
            throw new DomainValidationException("INVALID_PAGE_RANGE",
                    List.of("End page must be >= start page."));
        }
        this.endPage = endPage;
        this.durationMinutes = durationMinutes;
        this.state = ReadingState.PAUSED;
        markUpdated(Instant.now(clock), userId);
    }

    public void resume(int startPage, Clock clock) {
        validateStateTransition(ReadingState.STARTED);
        if (startPage > 0) this.startPage = startPage;
        this.state = ReadingState.STARTED;
        markUpdated(Instant.now(clock), userId);
    }

    public void finish(int endPage, int durationMinutes, Clock clock) {
        if (endPage < startPage) {
            throw new DomainValidationException("INVALID_PAGE_RANGE",
                    List.of("End page must be >= start page."));
        }
        this.endPage = endPage;
        this.durationMinutes = durationMinutes;
        this.state = ReadingState.COMPLETED;
        markUpdated(Instant.now(clock), userId);
    }

    public void cancel(Clock clock) {
        this.state = ReadingState.CANCELLED;
        markUpdated(Instant.now(clock), userId);
    }

    private void validateStateTransition(ReadingState target) {
        if (this.state == ReadingState.COMPLETED) {
            throw new DomainValidationException("INVALID_STATE",
                    List.of("Cannot modify a completed session."));
        }
        if (this.state == ReadingState.CANCELLED) {
            throw new DomainValidationException("INVALID_STATE",
                    List.of("Cannot modify a cancelled session."));
        }
        if (target == ReadingState.PAUSED && this.state != ReadingState.STARTED) {
            throw new DomainValidationException("INVALID_STATE",
                    List.of("Only started sessions can be paused."));
        }
        if (target == ReadingState.STARTED && this.state != ReadingState.PAUSED) {
            throw new DomainValidationException("INVALID_STATE",
                    List.of("Only paused sessions can be resumed."));
        }
    }

    public UUID getBookId() { return bookId; }
    public UUID getUserId() { return userId; }
    public Integer getStartPage() { return startPage; }
    public Integer getEndPage() { return endPage; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public ReadingState getState() { return state; }
}""")

w("reading/domain/event/ReadingStarted.java", """package app.vaj.reading.domain.event;

import app.vaj.common.domain.event.DomainEvent;
import java.time.Instant;
import java.util.UUID;

public class ReadingStarted extends DomainEvent {
    private final UUID sessionId;
    private final UUID bookId;
    private final UUID userId;

    public ReadingStarted(UUID eventId, Instant occurredAt, UUID sessionId, UUID bookId, UUID userId) {
        super(eventId, occurredAt);
        this.sessionId = sessionId;
        this.bookId = bookId;
        this.userId = userId;
    }

    public UUID getSessionId() { return sessionId; }
    public UUID getBookId() { return bookId; }
    public UUID getUserId() { return userId; }
}""")

w("reading/domain/repository/ReadingSessionRepository.java", """package app.vaj.reading.domain.repository;

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
}""")

w("reading/application/command/StartReadingCommand.java", """package app.vaj.reading.application.command;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record StartReadingCommand(
        @NotNull UUID bookId,
        @Min(1) int startPage
) {}""")

w("reading/application/command/PauseReadingCommand.java", """package app.vaj.reading.application.command;

import jakarta.validation.constraints.Min;

public record PauseReadingCommand(
        @Min(1) int endPage,
        @Min(1) int durationMinutes
) {}""")

w("reading/application/command/FinishReadingCommand.java", """package app.vaj.reading.application.command;

import jakarta.validation.constraints.Min;

public record FinishReadingCommand(
        @Min(1) int endPage,
        @Min(1) int durationMinutes
) {}""")

w("reading/application/dto/ReadingSessionResponse.java", """package app.vaj.reading.application.dto;

import java.time.Instant;
import java.util.UUID;

public record ReadingSessionResponse(
        UUID id, UUID bookId, Integer startPage, Integer endPage,
        Integer durationMinutes, String state, Instant createdAt, Instant updatedAt
) {}""")

w("reading/application/handler/StartReadingHandler.java", """package app.vaj.reading.application.handler;

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
}""")

w("reading/application/handler/PauseReadingHandler.java", """package app.vaj.reading.application.handler;

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
}""")

w("reading/application/handler/FinishReadingHandler.java", """package app.vaj.reading.application.handler;

import app.vaj.reading.application.command.FinishReadingCommand;
import app.vaj.reading.application.dto.ReadingSessionResponse;
import app.vaj.reading.domain.ReadingSession;
import app.vaj.reading.domain.repository.ReadingSessionRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class FinishReadingHandler {

    private final ReadingSessionRepository repository;
    private final Clock clock;

    public FinishReadingHandler(ReadingSessionRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    @Transactional
    public ReadingSessionResponse handle(UUID sessionId, FinishReadingCommand command) {
        ReadingSession session = repository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("ReadingSession", sessionId));
        session.finish(command.endPage(), command.durationMinutes(), clock);
        repository.save(session);
        return new ReadingSessionResponse(session.getId(), session.getBookId(), session.getStartPage(),
                session.getEndPage(), session.getDurationMinutes(), session.getState().name(),
                session.getCreatedAt(), session.getUpdatedAt());
    }
}""")

w("reading/application/handler/ListReadingSessionsHandler.java", """package app.vaj.reading.application.handler;

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
}""")

w("reading/infrastructure/persistence/ReadingSessionEntity.java", """package app.vaj.reading.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "ReadingSessions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReadingSessionEntity {
    @Id @Column(name = "Id") private UUID id;
    @Column(name = "BookId", nullable = false) private UUID bookId;
    @Column(name = "UserId", nullable = false) private UUID userId;
    @Column(name = "StartPage") private Integer startPage;
    @Column(name = "EndPage") private Integer endPage;
    @Column(name = "DurationMinutes") private Integer durationMinutes;
    @Column(name = "State", nullable = false, length = 20) @Enumerated(EnumType.STRING) private StateJpa state;
    @Column(name = "CreatedAt", nullable = false) private Instant createdAt;
    @Column(name = "CreatedBy") private UUID createdBy;
    @Column(name = "UpdatedAt") private Instant updatedAt;
    @Column(name = "UpdatedBy") private UUID updatedBy;
    @Column(name = "DeletedAt") private Instant deletedAt;
    @Column(name = "DeletedBy") private UUID deletedBy;
    @Column(name = "IsDeleted", nullable = false) private boolean isDeleted;
    @Version @Column(name = "Version") private Long version;

    public enum StateJpa { STARTED, PAUSED, COMPLETED, CANCELLED }
}""")

w("reading/infrastructure/persistence/JpaReadingSessionRepository.java", """package app.vaj.reading.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import java.util.UUID;

public interface JpaReadingSessionRepository extends JpaRepository<ReadingSessionEntity, UUID> {
    Optional<ReadingSessionEntity> findByIdAndIsDeletedFalse(UUID id);
    List<ReadingSessionEntity> findByBookIdAndIsDeletedFalseOrderByCreatedAtDesc(UUID bookId);
    Optional<ReadingSessionEntity> findByBookIdAndStateAndIsDeletedFalse(UUID bookId, ReadingSessionEntity.StateJpa state);
    boolean existsByBookIdAndStateAndIsDeletedFalse(UUID bookId, ReadingSessionEntity.StateJpa state);
}""")

w("reading/infrastructure/persistence/ReadingSessionRepositoryImpl.java", """package app.vaj.reading.infrastructure.persistence;

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
}""")

w("reading/api/ReadingSessionController.java", """package app.vaj.reading.api;

import app.vaj.reading.application.command.*;
import app.vaj.reading.application.dto.ReadingSessionResponse;
import app.vaj.reading.application.handler.*;
import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reading-sessions")
@Tag(name = "Reading Sessions", description = "Reading session management")
public class ReadingSessionController {

    private final StartReadingHandler startHandler;
    private final PauseReadingHandler pauseHandler;
    private final FinishReadingHandler finishHandler;
    private final ListReadingSessionsHandler listHandler;

    public ReadingSessionController(StartReadingHandler startHandler, PauseReadingHandler pauseHandler,
                                    FinishReadingHandler finishHandler, ListReadingSessionsHandler listHandler) {
        this.startHandler = startHandler;
        this.pauseHandler = pauseHandler;
        this.finishHandler = finishHandler;
        this.listHandler = listHandler;
    }

    @PostMapping
    @Operation(summary = "Start a reading session")
    public ResponseEntity<ApiResponse<ReadingSessionResponse>> start(
            @AuthenticationPrincipal CurrentUser user, @Valid @RequestBody StartReadingCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(startHandler.handle(user.id(), cmd)));
    }

    @PostMapping("/{id}/pause")
    @Operation(summary = "Pause reading session")
    public ResponseEntity<ApiResponse<ReadingSessionResponse>> pause(
            @PathVariable UUID id, @Valid @RequestBody PauseReadingCommand cmd) {
        return ResponseEntity.ok(ApiResponse.success(pauseHandler.handle(id, cmd)));
    }

    @PostMapping("/{id}/finish")
    @Operation(summary = "Finish reading session")
    public ResponseEntity<ApiResponse<ReadingSessionResponse>> finish(
            @PathVariable UUID id, @Valid @RequestBody FinishReadingCommand cmd) {
        return ResponseEntity.ok(ApiResponse.success(finishHandler.handle(id, cmd)));
    }

    @GetMapping
    @Operation(summary = "List reading sessions for a book")
    public ResponseEntity<ApiResponse<List<ReadingSessionResponse>>> list(@RequestParam UUID bookId) {
        return ResponseEntity.ok(ApiResponse.success(listHandler.handle(bookId)));
    }
}""")

print("=== Reading Session done ===")

# ============================================================
# BOOKMARK
# ============================================================
print("=== Bookmark ===")

w("bookmark/domain/Bookmark.java", """package app.vaj.bookmark.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bookmark extends BaseAggregateRoot {
    private UUID bookId;
    private UUID userId;
    private Integer page;
    private String chapter;
    private String title;
    private String description;
    private String color;
    private int sortOrder;

    private Bookmark(UUID id) { super(id); }

    public static Bookmark create(UUID id, UUID bookId, UUID userId, int page, String title, String description, String color, int sortOrder, Clock clock) {
        if (page < 1) throw new DomainValidationException("INVALID_PAGE", List.of("Page must be >= 1."));
        Bookmark b = new Bookmark(id);
        b.bookId = bookId; b.userId = userId; b.page = page;
        b.title = title; b.description = description; b.color = color; b.sortOrder = sortOrder;
        b.markCreated(Instant.now(clock), userId);
        return b;
    }

    public void update(String title, String description, String color, Integer page, Clock clock) {
        if (title != null) { if (title.length() > 200) throw new DomainValidationException("INVALID_TITLE", List.of("Title max 200 chars.")); this.title = title; }
        if (description != null) { if (description.length() > 1000) throw new DomainValidationException("INVALID_DESC", List.of("Description max 1000 chars.")); this.description = description; }
        if (color != null) this.color = color;
        if (page != null && page >= 1) this.page = page;
        markUpdated(Instant.now(clock), userId);
    }

    public void delete(Clock clock) { markDeleted(Instant.now(clock), userId); }

    public UUID getBookId() { return bookId; }
    public UUID getUserId() { return userId; }
    public Integer getPage() { return page; }
    public String getChapter() { return chapter; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getColor() { return color; }
    public int getSortOrder() { return sortOrder; }
}""")

w("bookmark/domain/repository/BookmarkRepository.java", """package app.vaj.bookmark.domain.repository;

import app.vaj.bookmark.domain.Bookmark;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookmarkRepository {
    Bookmark save(Bookmark bookmark);
    Optional<Bookmark> findById(UUID id);
    List<Bookmark> findByBookId(UUID bookId);
    void delete(Bookmark bookmark);
}""")

w("bookmark/application/command/CreateBookmarkCommand.java", """package app.vaj.bookmark.application.command;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateBookmarkCommand(
        @NotNull UUID bookId,
        @Min(1) int page,
        String title,
        String description,
        String color
) {}""")

w("bookmark/application/command/UpdateBookmarkCommand.java", """package app.vaj.bookmark.application.command;

import jakarta.validation.constraints.Min;
import java.util.UUID;

public record UpdateBookmarkCommand(
        String title,
        String description,
        String color,
        @Min(1) Integer page
) {}""")

w("bookmark/application/dto/BookmarkResponse.java", """package app.vaj.bookmark.application.dto;

import java.time.Instant;
import java.util.UUID;

public record BookmarkResponse(
        UUID id, UUID bookId, Integer page, String title, String description,
        String color, int sortOrder, Instant createdAt
) {}""")

w("bookmark/application/handler/CreateBookmarkHandler.java", """package app.vaj.bookmark.application.handler;

import app.vaj.bookmark.application.command.CreateBookmarkCommand;
import app.vaj.bookmark.application.dto.BookmarkResponse;
import app.vaj.bookmark.domain.Bookmark;
import app.vaj.bookmark.domain.repository.BookmarkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;

@Service
public class CreateBookmarkHandler {
    private final BookmarkRepository repository;
    private final Clock clock;
    public CreateBookmarkHandler(BookmarkRepository repository, Clock clock) { this.repository = repository; this.clock = clock; }

    @Transactional
    public BookmarkResponse handle(UUID userId, CreateBookmarkCommand cmd) {
        Bookmark b = Bookmark.create(UUID.randomUUID(), cmd.bookId(), userId, cmd.page(),
                cmd.title(), cmd.description(), cmd.color(), 0, clock);
        repository.save(b);
        return new BookmarkResponse(b.getId(), b.getBookId(), b.getPage(), b.getTitle(),
                b.getDescription(), b.getColor(), b.getSortOrder(), b.getCreatedAt());
    }
}""")

w("bookmark/application/handler/UpdateBookmarkHandler.java", """package app.vaj.bookmark.application.handler;

import app.vaj.bookmark.application.command.UpdateBookmarkCommand;
import app.vaj.bookmark.application.dto.BookmarkResponse;
import app.vaj.bookmark.domain.Bookmark;
import app.vaj.bookmark.domain.repository.BookmarkRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;

@Service
public class UpdateBookmarkHandler {
    private final BookmarkRepository repository;
    private final Clock clock;
    public UpdateBookmarkHandler(BookmarkRepository repository, Clock clock) { this.repository = repository; this.clock = clock; }

    @Transactional
    public BookmarkResponse handle(UUID id, UpdateBookmarkCommand cmd) {
        Bookmark b = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bookmark", id));
        b.update(cmd.title(), cmd.description(), cmd.color(), cmd.page(), clock);
        repository.save(b);
        return new BookmarkResponse(b.getId(), b.getBookId(), b.getPage(), b.getTitle(),
                b.getDescription(), b.getColor(), b.getSortOrder(), b.getCreatedAt());
    }
}""")

w("bookmark/application/handler/DeleteBookmarkHandler.java", """package app.vaj.bookmark.application.handler;

import app.vaj.bookmark.domain.Bookmark;
import app.vaj.bookmark.domain.repository.BookmarkRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;

@Service
public class DeleteBookmarkHandler {
    private final BookmarkRepository repository;
    private final Clock clock;
    public DeleteBookmarkHandler(BookmarkRepository repository, Clock clock) { this.repository = repository; this.clock = clock; }

    @Transactional
    public void handle(UUID id) {
        Bookmark b = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bookmark", id));
        b.delete(clock);
        repository.delete(b);
    }
}""")

w("bookmark/application/handler/ListBookmarksHandler.java", """package app.vaj.bookmark.application.handler;

import app.vaj.bookmark.application.dto.BookmarkResponse;
import app.vaj.bookmark.domain.Bookmark;
import app.vaj.bookmark.domain.repository.BookmarkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ListBookmarksHandler {
    private final BookmarkRepository repository;
    public ListBookmarksHandler(BookmarkRepository repository) { this.repository = repository; }

    @Transactional(readOnly = true)
    public List<BookmarkResponse> handle(UUID bookId) {
        return repository.findByBookId(bookId).stream()
                .map(b -> new BookmarkResponse(b.getId(), b.getBookId(), b.getPage(), b.getTitle(),
                        b.getDescription(), b.getColor(), b.getSortOrder(), b.getCreatedAt()))
                .toList();
    }
}""")

w("bookmark/infrastructure/persistence/BookmarkEntity.java", """package app.vaj.bookmark.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "Bookmarks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BookmarkEntity {
    @Id @Column(name = "Id") private UUID id;
    @Column(name = "BookId", nullable = false) private UUID bookId;
    @Column(name = "UserId", nullable = false) private UUID userId;
    @Column(name = "Page") private Integer page;
    @Column(name = "Chapter", length = 200) private String chapter;
    @Column(name = "Title", length = 200) private String title;
    @Column(name = "Description", length = 1000) private String description;
    @Column(name = "Color", length = 20) private String color;
    @Column(name = "SortOrder", nullable = false) private int sortOrder;
    @Column(name = "CreatedAt", nullable = false) private Instant createdAt;
    @Column(name = "CreatedBy") private UUID createdBy;
    @Column(name = "UpdatedAt") private Instant updatedAt;
    @Column(name = "DeletedAt") private Instant deletedAt;
    @Column(name = "DeletedBy") private UUID deletedBy;
    @Column(name = "IsDeleted", nullable = false) private boolean isDeleted;
    @Version @Column(name = "Version") private Long version;
}""")

w("bookmark/infrastructure/persistence/JpaBookmarkRepository.java", """package app.vaj.bookmark.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import java.util.UUID;

public interface JpaBookmarkRepository extends JpaRepository<BookmarkEntity, UUID> {
    Optional<BookmarkEntity> findByIdAndIsDeletedFalse(UUID id);
    List<BookmarkEntity> findByBookIdAndIsDeletedFalseOrderBySortOrderAsc(UUID bookId);
}""")

w("bookmark/infrastructure/persistence/BookmarkRepositoryImpl.java", """package app.vaj.bookmark.infrastructure.persistence;

import app.vaj.bookmark.domain.Bookmark;
import app.vaj.bookmark.domain.repository.BookmarkRepository;
import org.springframework.stereotype.Repository;
import java.lang.reflect.Field;
import java.util.*;

@Repository
public class BookmarkRepositoryImpl implements BookmarkRepository {
    private final JpaBookmarkRepository jpa;
    public BookmarkRepositoryImpl(JpaBookmarkRepository jpa) { this.jpa = jpa; }

    @Override public Bookmark save(Bookmark b) { jpa.save(toEntity(b)); return b; }
    @Override public Optional<Bookmark> findById(UUID id) { return jpa.findByIdAndIsDeletedFalse(id).map(this::toDomain); }
    @Override public List<Bookmark> findByBookId(UUID bookId) { return jpa.findByBookIdAndIsDeletedFalseOrderBySortOrderAsc(bookId).stream().map(this::toDomain).toList(); }
    @Override public void delete(Bookmark b) { jpa.save(toEntity(b)); }

    private Bookmark toDomain(BookmarkEntity e) {
        try {
            var c = Bookmark.class.getDeclaredConstructor(UUID.class); c.setAccessible(true);
            Bookmark b = c.newInstance(e.getId());
            set(b, "bookId", e.getBookId()); set(b, "userId", e.getUserId()); set(b, "page", e.getPage());
            set(b, "chapter", e.getChapter()); set(b, "title", e.getTitle()); set(b, "description", e.getDescription());
            set(b, "color", e.getColor()); set(b, "sortOrder", e.getSortOrder());
            set(b, "createdAt", e.getCreatedAt()); set(b, "updatedAt", e.getUpdatedAt());
            return b;
        } catch (Exception ex) { throw new RuntimeException("Mapping failed", ex); }
    }

    private BookmarkEntity toEntity(Bookmark b) {
        BookmarkEntity e = new BookmarkEntity();
        e.setId(b.getId()); e.setBookId(b.getBookId()); e.setUserId(b.getUserId()); e.setPage(b.getPage());
        e.setChapter(b.getChapter()); e.setTitle(b.getTitle()); e.setDescription(b.getDescription());
        e.setColor(b.getColor()); e.setSortOrder(b.getSortOrder());
        e.setCreatedAt(b.getCreatedAt() != null ? b.getCreatedAt() : java.time.Instant.now());
        e.setUpdatedAt(b.getUpdatedAt() != null ? b.getUpdatedAt() : java.time.Instant.now());
        e.setCreatedBy(b.getUserId()); e.setVersion(b.getVersion() != null ? b.getVersion() : 0L);
        e.setDeleted(b.isDeleted());
        return e;
    }

    private void set(Object t, String n, Object v) {
        try { Field f = t.getClass().getDeclaredField(n); f.setAccessible(true); f.set(t, v); }
        catch (Exception e) { try { Field f = t.getClass().getSuperclass().getDeclaredField(n); f.setAccessible(true); f.set(t, v); } catch (Exception ignored) {} }
    }
}""")

w("bookmark/api/BookmarkController.java", """package app.vaj.bookmark.api;

import app.vaj.bookmark.application.command.CreateBookmarkCommand;
import app.vaj.bookmark.application.command.UpdateBookmarkCommand;
import app.vaj.bookmark.application.dto.BookmarkResponse;
import app.vaj.bookmark.application.handler.*;
import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Bookmarks", description = "Bookmark management")
public class BookmarkController {

    private final CreateBookmarkHandler createHandler;
    private final UpdateBookmarkHandler updateHandler;
    private final DeleteBookmarkHandler deleteHandler;
    private final ListBookmarksHandler listHandler;

    public BookmarkController(CreateBookmarkHandler createHandler, UpdateBookmarkHandler updateHandler,
                              DeleteBookmarkHandler deleteHandler, ListBookmarksHandler listHandler) {
        this.createHandler = createHandler; this.updateHandler = updateHandler;
        this.deleteHandler = deleteHandler; this.listHandler = listHandler;
    }

    @GetMapping("/api/v1/books/{bookId}/bookmarks")
    @Operation(summary = "List bookmarks for a book")
    public ResponseEntity<ApiResponse<List<BookmarkResponse>>> list(@PathVariable UUID bookId) {
        return ResponseEntity.ok(ApiResponse.success(listHandler.handle(bookId)));
    }

    @PostMapping("/api/v1/books/{bookId}/bookmarks")
    @Operation(summary = "Create a bookmark")
    public ResponseEntity<ApiResponse<BookmarkResponse>> create(
            @AuthenticationPrincipal CurrentUser user, @PathVariable UUID bookId,
            @Valid @RequestBody CreateBookmarkCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(createHandler.handle(user.id(),
                new CreateBookmarkCommand(bookId, cmd.page(), cmd.title(), cmd.description(), cmd.color()))));
    }

    @PatchMapping("/api/v1/bookmarks/{id}")
    @Operation(summary = "Update a bookmark")
    public ResponseEntity<ApiResponse<BookmarkResponse>> update(@PathVariable UUID id, @Valid @RequestBody UpdateBookmarkCommand cmd) {
        return ResponseEntity.ok(ApiResponse.success(updateHandler.handle(id, cmd)));
    }

    @DeleteMapping("/api/v1/bookmarks/{id}")
    @Operation(summary = "Delete a bookmark")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        deleteHandler.handle(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}""")

print("=== Bookmark done ===")

# ============================================================
# SQL MIGRATION
# ============================================================
print("=== SQL Migration ===")

sql("V012__Create_Reading_Bookmark_Goal.sql", """CREATE TABLE ReadingSessions (
    Id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    BookId UNIQUEIDENTIFIER NOT NULL,
    UserId UNIQUEIDENTIFIER NOT NULL,
    StartPage INT NULL,
    EndPage INT NULL,
    DurationMinutes INT NULL,
    State NVARCHAR(20) NOT NULL DEFAULT 'STARTED',
    CreatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
    CreatedBy UNIQUEIDENTIFIER NULL,
    UpdatedAt DATETIME2 NULL,
    UpdatedBy UNIQUEIDENTIFIER NULL,
    DeletedAt DATETIME2 NULL,
    DeletedBy UNIQUEIDENTIFIER NULL,
    IsDeleted BIT NOT NULL DEFAULT 0,
    Version ROWVERSION NOT NULL
);
CREATE NONCLUSTERED INDEX IX_ReadingSessions_BookId ON ReadingSessions(BookId) WHERE IsDeleted = 0;
CREATE NONCLUSTERED INDEX IX_ReadingSessions_UserId ON ReadingSessions(UserId) WHERE IsDeleted = 0;

CREATE TABLE Bookmarks (
    Id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    BookId UNIQUEIDENTIFIER NOT NULL,
    UserId UNIQUEIDENTIFIER NOT NULL,
    Page INT NULL,
    Chapter NVARCHAR(200) NULL,
    Title NVARCHAR(200) NULL,
    Description NVARCHAR(1000) NULL,
    Color NVARCHAR(20) NULL,
    SortOrder INT NOT NULL DEFAULT 0,
    CreatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
    CreatedBy UNIQUEIDENTIFIER NULL,
    UpdatedAt DATETIME2 NULL,
    UpdatedBy UNIQUEIDENTIFIER NULL,
    DeletedAt DATETIME2 NULL,
    DeletedBy UNIQUEIDENTIFIER NULL,
    IsDeleted BIT NOT NULL DEFAULT 0,
    Version ROWVERSION NOT NULL
);
CREATE NONCLUSTERED INDEX IX_Bookmarks_BookId ON Bookmarks(BookId) WHERE IsDeleted = 0;

CREATE TABLE ReadingGoals (
    Id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    UserId UNIQUEIDENTIFIER NOT NULL,
    GoalType NVARCHAR(50) NOT NULL,
    Target INT NOT NULL,
    CurrentProgress INT NOT NULL DEFAULT 0,
    StartDate DATETIME2 NOT NULL,
    EndDate DATETIME2 NOT NULL,
    Status NVARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    CreatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
    CreatedBy UNIQUEIDENTIFIER NULL,
    UpdatedAt DATETIME2 NULL,
    UpdatedBy UNIQUEIDENTIFIER NULL,
    DeletedAt DATETIME2 NULL,
    DeletedBy UNIQUEIDENTIFIER NULL,
    IsDeleted BIT NOT NULL DEFAULT 0,
    Version ROWVERSION NOT NULL
);
CREATE NONCLUSTERED INDEX IX_ReadingGoals_UserId ON ReadingGoals(UserId) WHERE IsDeleted = 0;
CREATE NONCLUSTERED INDEX IX_ReadingGoals_Status ON ReadingGoals(Status) WHERE IsDeleted = 0;
""")

sql("V013__Create_Knowledge_Tables.sql", """CREATE TABLE Highlights (
    Id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    BookId UNIQUEIDENTIFIER NOT NULL,
    UserId UNIQUEIDENTIFIER NOT NULL,
    Page INT NULL,
    StartPosition INT NULL,
    EndPosition INT NULL,
    TextSnapshot NVARCHAR(MAX) NOT NULL,
    Color NVARCHAR(20) NOT NULL DEFAULT 'YELLOW',
    Comment NVARCHAR(2000) NULL,
    Status NVARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    CreatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
    CreatedBy UNIQUEIDENTIFIER NULL,
    UpdatedAt DATETIME2 NULL,
    UpdatedBy UNIQUEIDENTIFIER NULL,
    DeletedAt DATETIME2 NULL,
    DeletedBy UNIQUEIDENTIFIER NULL,
    IsDeleted BIT NOT NULL DEFAULT 0,
    Version ROWVERSION NOT NULL
);
CREATE NONCLUSTERED INDEX IX_Highlights_BookId ON Highlights(BookId) WHERE IsDeleted = 0;
CREATE NONCLUSTERED INDEX IX_Highlights_UserId ON Highlights(UserId) WHERE IsDeleted = 0;

CREATE TABLE KnowledgeNotes (
    Id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    UserId UNIQUEIDENTIFIER NOT NULL,
    Title NVARCHAR(300) NULL,
    Content NVARCHAR(MAX) NOT NULL,
    Status NVARCHAR(50) NOT NULL DEFAULT 'DRAFT',
    CreatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
    CreatedBy UNIQUEIDENTIFIER NULL,
    UpdatedAt DATETIME2 NULL,
    UpdatedBy UNIQUEIDENTIFIER NULL,
    DeletedAt DATETIME2 NULL,
    DeletedBy UNIQUEIDENTIFIER NULL,
    IsDeleted BIT NOT NULL DEFAULT 0,
    Version ROWVERSION NOT NULL
);
CREATE NONCLUSTERED INDEX IX_KnowledgeNotes_UserId ON KnowledgeNotes(UserId) WHERE IsDeleted = 0;
CREATE NONCLUSTERED INDEX IX_KnowledgeNotes_Status ON KnowledgeNotes(Status) WHERE IsDeleted = 0;

CREATE TABLE Collections (
    Id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    UserId UNIQUEIDENTIFIER NOT NULL,
    Name NVARCHAR(200) NOT NULL,
    Description NVARCHAR(1000) NULL,
    Status NVARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    CreatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
    CreatedBy UNIQUEIDENTIFIER NULL,
    UpdatedAt DATETIME2 NULL,
    UpdatedBy UNIQUEIDENTIFIER NULL,
    DeletedAt DATETIME2 NULL,
    DeletedBy UNIQUEIDENTIFIER NULL,
    IsDeleted BIT NOT NULL DEFAULT 0,
    Version ROWVERSION NOT NULL,
    CONSTRAINT UQ_Collections_UserId_Name WHERE IsDeleted = 0 UNIQUE (UserId, Name)
);
CREATE NONCLUSTERED INDEX IX_Collections_UserId ON Collections(UserId) WHERE IsDeleted = 0;

CREATE TABLE CollectionItems (
    Id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY DEFAULT NEWID(),
    CollectionId UNIQUEIDENTIFIER NOT NULL,
    ItemType NVARCHAR(50) NOT NULL,
    ItemId UNIQUEIDENTIFIER NOT NULL,
    [Order] INT NOT NULL DEFAULT 0,
    CreatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
    CONSTRAINT FK_CollectionItems_Collections FOREIGN KEY (CollectionId) REFERENCES Collections(Id),
    CONSTRAINT UQ_CollectionItems_Collection_Item UNIQUE (CollectionId, ItemType, ItemId)
);

CREATE TABLE Tags (
    Id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    UserId UNIQUEIDENTIFIER NOT NULL,
    Name NVARCHAR(100) NOT NULL,
    Color NVARCHAR(7) NULL,
    Description NVARCHAR(500) NULL,
    Status NVARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    CreatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
    CreatedBy UNIQUEIDENTIFIER NULL,
    UpdatedAt DATETIME2 NULL,
    UpdatedBy UNIQUEIDENTIFIER NULL,
    DeletedAt DATETIME2 NULL,
    DeletedBy UNIQUEIDENTIFIER NULL,
    IsDeleted BIT NOT NULL DEFAULT 0,
    Version ROWVERSION NOT NULL,
    CONSTRAINT UQ_Tags_UserId_Name WHERE IsDeleted = 0 UNIQUE (UserId, Name)
);
CREATE NONCLUSTERED INDEX IX_Tags_UserId ON Tags(UserId) WHERE IsDeleted = 0;

CREATE TABLE BookTags (
    BookId UNIQUEIDENTIFIER NOT NULL,
    TagId UNIQUEIDENTIFIER NOT NULL,
    CONSTRAINT FK_BookTags_Books FOREIGN KEY (BookId) REFERENCES Books(Id),
    CONSTRAINT FK_BookTags_Tags FOREIGN KEY (TagId) REFERENCES Tags(Id),
    CONSTRAINT PK_BookTags PRIMARY KEY (BookId, TagId)
);

CREATE TABLE HighlightTags (
    HighlightId UNIQUEIDENTIFIER NOT NULL,
    TagId UNIQUEIDENTIFIER NOT NULL,
    CONSTRAINT FK_HighlightTags_Highlights FOREIGN KEY (HighlightId) REFERENCES Highlights(Id),
    CONSTRAINT FK_HighlightTags_Tags FOREIGN KEY (TagId) REFERENCES Tags(Id),
    CONSTRAINT PK_HighlightTags PRIMARY KEY (HighlightId, TagId)
);

CREATE TABLE KnowledgeNoteBooks (
    NoteId UNIQUEIDENTIFIER NOT NULL,
    BookId UNIQUEIDENTIFIER NOT NULL,
    CONSTRAINT FK_KnowledgeNoteBooks_Notes FOREIGN KEY (NoteId) REFERENCES KnowledgeNotes(Id),
    CONSTRAINT FK_KnowledgeNoteBooks_Books FOREIGN KEY (BookId) REFERENCES Books(Id),
    CONSTRAINT PK_KnowledgeNoteBooks PRIMARY KEY (NoteId, BookId)
);

CREATE TABLE KnowledgeNoteHighlights (
    NoteId UNIQUEIDENTIFIER NOT NULL,
    HighlightId UNIQUEIDENTIFIER NOT NULL,
    CONSTRAINT FK_KnowledgeNoteHighlights_Notes FOREIGN KEY (NoteId) REFERENCES KnowledgeNotes(Id),
    CONSTRAINT FK_KnowledgeNoteHighlights_Highlights FOREIGN KEY (HighlightId) REFERENCES Highlights(Id),
    CONSTRAINT PK_KnowledgeNoteHighlights PRIMARY KEY (NoteId, HighlightId)
);
""")

print("=== SQL Migrations done ===")
print("Phase 1 complete. Run with: python3 generate_remaining.py")