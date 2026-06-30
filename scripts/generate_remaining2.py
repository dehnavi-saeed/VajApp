#!/usr/bin/env python3
"""Generate Goal, Highlight, Note, Collection, Tag features."""
import os

BASE = "/home/z/my-project/download/vaj/backend/src/main/java/app/vaj"

def w(path, content):
    full = os.path.join(BASE, path)
    os.makedirs(os.path.dirname(full), exist_ok=True)
    with open(full, 'w') as f:
        f.write(content)
    print(f"  OK: {path}")

# ============================================================
# READING GOAL
# ============================================================
print("=== Reading Goal ===")

w("goal/domain/GoalType.java", "package app.vaj.goal.domain;\n\npublic enum GoalType {\n    DAILY_PAGES, DAILY_MINUTES, WEEKLY_PAGES, MONTHLY_BOOKS, READING_STREAK\n}")

w("goal/domain/GoalStatus.java", "package app.vaj.goal.domain;\n\npublic enum GoalStatus {\n    DRAFT, ACTIVE, PAUSED, COMPLETED, CANCELLED, ARCHIVED\n}")

w("goal/domain/ReadingGoal.java", """package app.vaj.goal.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;
import java.time.*;
import java.util.*;

public class ReadingGoal extends BaseAggregateRoot {
    private UUID userId;
    private GoalType type;
    private int target;
    private int currentProgress;
    private Instant startDate;
    private Instant endDate;
    private GoalStatus status;

    private ReadingGoal(UUID id) { super(id); }

    public static ReadingGoal create(UUID id, UUID userId, GoalType type, int target, Instant startDate, Instant endDate, Clock clock) {
        if (target < 1) throw new DomainValidationException("INVALID_TARGET", List.of("Target must be positive."));
        if (endDate != null && startDate != null && !endDate.isAfter(startDate)) {
            throw new DomainValidationException("INVALID_DATES", List.of("End date must be after start date."));
        }
        ReadingGoal g = new ReadingGoal(id);
        g.userId = userId; g.type = type; g.target = target; g.currentProgress = 0;
        g.startDate = startDate; g.endDate = endDate; g.status = GoalStatus.ACTIVE;
        g.markCreated(Instant.now(clock), userId);
        return g;
    }

    public void update(int target, Instant startDate, Instant endDate, Clock clock) {
        if (target > 0) this.target = target;
        if (startDate != null) this.startDate = startDate;
        if (endDate != null) this.endDate = endDate;
        markUpdated(Instant.now(clock), userId);
    }

    public void pause(Clock clock) { this.status = GoalStatus.PAUSED; markUpdated(Instant.now(clock), userId); }
    public void resume(Clock clock) { this.status = GoalStatus.ACTIVE; markUpdated(Instant.now(clock), userId); }
    public void complete(Clock clock) { this.status = GoalStatus.COMPLETED; markUpdated(Instant.now(clock), userId); }
    public void cancel(Clock clock) { this.status = GoalStatus.CANCELLED; markUpdated(Instant.now(clock), userId); }

    public UUID getUserId() { return userId; }
    public GoalType getType() { return type; }
    public int getTarget() { return target; }
    public int getCurrentProgress() { return currentProgress; }
    public Instant getStartDate() { return startDate; }
    public Instant getEndDate() { return endDate; }
    public GoalStatus getStatus() { return status; }
}""")

w("goal/domain/event/ReadingGoalCreated.java", """package app.vaj.goal.domain.event;
import app.vaj.common.domain.event.DomainEvent;
import java.time.Instant;
import java.util.UUID;
public class ReadingGoalCreated extends DomainEvent {
    private final UUID goalId; private final UUID userId; private final String type;
    public ReadingGoalCreated(UUID eid, Instant at, UUID goalId, UUID userId, String type) { super(eid, at); this.goalId = goalId; this.userId = userId; this.type = type; }
    public UUID getGoalId() { return goalId; }
    public UUID getUserId() { return userId; }
    public String getType() { return type; }
}""")

w("goal/domain/repository/ReadingGoalRepository.java", """package app.vaj.goal.domain.repository;
import app.vaj.goal.domain.ReadingGoal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface ReadingGoalRepository {
    ReadingGoal save(ReadingGoal goal);
    Optional<ReadingGoal> findById(UUID id);
    List<ReadingGoal> findByUserId(UUID userId);
}""")

w("goal/application/command/CreateGoalCommand.java", """package app.vaj.goal.application.command;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
public record CreateGoalCommand(
    @NotNull String type, @Min(1) int target, @NotNull Instant startDate, Instant endDate
) {}""")

w("goal/application/command/UpdateGoalCommand.java", """package app.vaj.goal.application.command;
import jakarta.validation.constraints.Min;
import java.time.Instant;
public record UpdateGoalCommand(@Min(1) Integer target, Instant startDate, Instant endDate) {}""")

w("goal/application/dto/ReadingGoalResponse.java", """package app.vaj.goal.application.dto;
import java.time.Instant;
import java.util.UUID;
public record ReadingGoalResponse(
    UUID id, String type, int target, int currentProgress,
    Instant startDate, Instant endDate, String status, Instant createdAt
) {}""")

w("goal/application/handler/CreateGoalHandler.java", """package app.vaj.goal.application.handler;
import app.vaj.goal.application.command.CreateGoalCommand;
import app.vaj.goal.application.dto.ReadingGoalResponse;
import app.vaj.goal.domain.*;
import app.vaj.goal.domain.event.ReadingGoalCreated;
import app.vaj.goal.domain.repository.ReadingGoalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.time.Instant;
import java.util.UUID;
@Service
public class CreateGoalHandler {
    private final ReadingGoalRepository repo; private final Clock clock;
    public CreateGoalHandler(ReadingGoalRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public ReadingGoalResponse handle(UUID userId, CreateGoalCommand cmd) {
        UUID id = UUID.randomUUID();
        GoalType type = GoalType.valueOf(cmd.type());
        ReadingGoal g = ReadingGoal.create(id, userId, type, cmd.target(), cmd.startDate(), cmd.endDate(), clock);
        g.registerEvent(new ReadingGoalCreated(UUID.randomUUID(), Instant.now(clock), id, userId, cmd.type()));
        repo.save(g);
        return new ReadingGoalResponse(g.getId(), g.getType().name(), g.getTarget(), g.getCurrentProgress(),
                g.getStartDate(), g.getEndDate(), g.getStatus().name(), g.getCreatedAt());
    }
}""")

w("goal/application/handler/PauseGoalHandler.java", """package app.vaj.goal.application.handler;
import app.vaj.goal.application.dto.ReadingGoalResponse;
import app.vaj.goal.domain.ReadingGoal;
import app.vaj.goal.domain.repository.ReadingGoalRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;
@Service
public class PauseGoalHandler {
    private final ReadingGoalRepository repo; private final Clock clock;
    public PauseGoalHandler(ReadingGoalRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public ReadingGoalResponse handle(UUID id) {
        ReadingGoal g = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("ReadingGoal", id));
        g.pause(clock); repo.save(g);
        return new ReadingGoalResponse(g.getId(), g.getType().name(), g.getTarget(), g.getCurrentProgress(),
                g.getStartDate(), g.getEndDate(), g.getStatus().name(), g.getCreatedAt());
    }
}""")

w("goal/application/handler/ResumeGoalHandler.java", """package app.vaj.goal.application.handler;
import app.vaj.goal.application.dto.ReadingGoalResponse;
import app.vaj.goal.domain.ReadingGoal;
import app.vaj.goal.domain.repository.ReadingGoalRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;
@Service
public class ResumeGoalHandler {
    private final ReadingGoalRepository repo; private final Clock clock;
    public ResumeGoalHandler(ReadingGoalRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public ReadingGoalResponse handle(UUID id) {
        ReadingGoal g = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("ReadingGoal", id));
        g.resume(clock); repo.save(g);
        return new ReadingGoalResponse(g.getId(), g.getType().name(), g.getTarget(), g.getCurrentProgress(),
                g.getStartDate(), g.getEndDate(), g.getStatus().name(), g.getCreatedAt());
    }
}""")

w("goal/application/handler/CancelGoalHandler.java", """package app.vaj.goal.application.handler;
import app.vaj.goal.domain.ReadingGoal;
import app.vaj.goal.domain.repository.ReadingGoalRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;
@Service
public class CancelGoalHandler {
    private final ReadingGoalRepository repo; private final Clock clock;
    public CancelGoalHandler(ReadingGoalRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public void handle(UUID id) {
        ReadingGoal g = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("ReadingGoal", id));
        g.cancel(clock); repo.save(g);
    }
}""")

w("goal/application/handler/ListGoalsHandler.java", """package app.vaj.goal.application.handler;
import app.vaj.goal.application.dto.ReadingGoalResponse;
import app.vaj.goal.domain.ReadingGoal;
import app.vaj.goal.domain.repository.ReadingGoalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
@Service
public class ListGoalsHandler {
    private final ReadingGoalRepository repo;
    public ListGoalsHandler(ReadingGoalRepository repo) { this.repo = repo; }
    @Transactional(readOnly = true)
    public List<ReadingGoalResponse> handle(UUID userId) {
        return repo.findByUserId(userId).stream()
                .map(g -> new ReadingGoalResponse(g.getId(), g.getType().name(), g.getTarget(),
                        g.getCurrentProgress(), g.getStartDate(), g.getEndDate(), g.getStatus().name(), g.getCreatedAt()))
                .toList();
    }
}""")

w("goal/infrastructure/persistence/ReadingGoalEntity.java", """package app.vaj.goal.infrastructure.persistence;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;
@Entity
@Table(name = "ReadingGoals")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReadingGoalEntity {
    @Id @Column(name = "Id") private UUID id;
    @Column(name = "UserId", nullable = false) private UUID userId;
    @Column(name = "GoalType", nullable = false, length = 50) @Enumerated(EnumType.STRING) private TypeJpa goalType;
    @Column(name = "Target", nullable = false) private int target;
    @Column(name = "CurrentProgress", nullable = false) private int currentProgress;
    @Column(name = "StartDate", nullable = false) private Instant startDate;
    @Column(name = "EndDate") private Instant endDate;
    @Column(name = "Status", nullable = false, length = 50) @Enumerated(EnumType.STRING) private StatusJpa status;
    @Column(name = "CreatedAt", nullable = false) private Instant createdAt;
    @Column(name = "CreatedBy") private UUID createdBy;
    @Column(name = "UpdatedAt") private Instant updatedAt;
    @Column(name = "UpdatedBy") private UUID updatedBy;
    @Column(name = "DeletedAt") private Instant deletedAt;
    @Column(name = "DeletedBy") private UUID deletedBy;
    @Column(name = "IsDeleted", nullable = false) private boolean isDeleted;
    @Version @Column(name = "Version") private Long version;
    public enum TypeJpa { DAILY_PAGES, DAILY_MINUTES, WEEKLY_PAGES, MONTHLY_BOOKS, READING_STREAK }
    public enum StatusJpa { DRAFT, ACTIVE, PAUSED, COMPLETED, CANCELLED, ARCHIVED }
}""")

w("goal/infrastructure/persistence/JpaReadingGoalRepository.java", """package app.vaj.goal.infrastructure.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import java.util.UUID;
public interface JpaReadingGoalRepository extends JpaRepository<ReadingGoalEntity, UUID> {
    Optional<ReadingGoalEntity> findByIdAndIsDeletedFalse(UUID id);
    List<ReadingGoalEntity> findByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(UUID userId);
}""")

w("goal/infrastructure/persistence/ReadingGoalRepositoryImpl.java", """package app.vaj.goal.infrastructure.persistence;
import app.vaj.goal.domain.*;
import app.vaj.goal.domain.repository.ReadingGoalRepository;
import org.springframework.stereotype.Repository;
import java.lang.reflect.Field;
import java.util.*;
@Repository
public class ReadingGoalRepositoryImpl implements ReadingGoalRepository {
    private final JpaReadingGoalRepository jpa;
    public ReadingGoalRepositoryImpl(JpaReadingGoalRepository jpa) { this.jpa = jpa; }
    @Override public ReadingGoal save(ReadingGoal g) { jpa.save(toEntity(g)); return g; }
    @Override public Optional<ReadingGoal> findById(UUID id) { return jpa.findByIdAndIsDeletedFalse(id).map(this::toDomain); }
    @Override public List<ReadingGoal> findByUserId(UUID userId) { return jpa.findByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(userId).stream().map(this::toDomain).toList(); }

    private ReadingGoal toDomain(ReadingGoalEntity e) {
        try {
            var c = ReadingGoal.class.getDeclaredConstructor(UUID.class); c.setAccessible(true);
            ReadingGoal g = c.newInstance(e.getId());
            set(g,"userId",e.getUserId()); set(g,"type",GoalType.valueOf(e.getGoalType().name()));
            set(g,"target",e.getTarget()); set(g,"currentProgress",e.getCurrentProgress());
            set(g,"startDate",e.getStartDate()); set(g,"endDate",e.getEndDate());
            set(g,"status",GoalStatus.valueOf(e.getStatus().name()));
            set(g,"createdAt",e.getCreatedAt()); set(g,"updatedAt",e.getUpdatedAt());
            return g;
        } catch (Exception ex) { throw new RuntimeException("Mapping failed", ex); }
    }
    private ReadingGoalEntity toEntity(ReadingGoal g) {
        ReadingGoalEntity e = new ReadingGoalEntity();
        e.setId(g.getId()); e.setUserId(g.getUserId());
        e.setGoalType(ReadingGoalEntity.TypeJpa.valueOf(g.getType().name()));
        e.setTarget(g.getTarget()); e.setCurrentProgress(g.getCurrentProgress());
        e.setStartDate(g.getStartDate()); e.setEndDate(g.getEndDate());
        e.setStatus(ReadingGoalEntity.StatusJpa.valueOf(g.getStatus().name()));
        e.setCreatedAt(g.getCreatedAt()!=null?g.getCreatedAt():java.time.Instant.now());
        e.setUpdatedAt(g.getUpdatedAt()!=null?g.getUpdatedAt():java.time.Instant.now());
        e.setCreatedBy(g.getUserId()); e.setVersion(g.getVersion()!=null?g.getVersion():0L); e.setDeleted(g.isDeleted());
        return e;
    }
    private void set(Object t,String n,Object v) {
        try { Field f=t.getClass().getDeclaredField(n); f.setAccessible(true); f.set(t,v); }
        catch(Exception e) { try { Field f=t.getClass().getSuperclass().getDeclaredField(n); f.setAccessible(true); f.set(t,v); } catch(Exception ignored) {} }
    }
}""")

w("goal/api/GoalController.java", """package app.vaj.goal.api;
import app.vaj.goal.application.command.CreateGoalCommand;
import app.vaj.goal.application.dto.ReadingGoalResponse;
import app.vaj.goal.application.handler.*;
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
@RequestMapping("/api/v1/reading-goals")
@Tag(name = "Reading Goals", description = "Reading goal management")
public class GoalController {
    private final CreateGoalHandler createHandler;
    private final PauseGoalHandler pauseHandler;
    private final ResumeGoalHandler resumeHandler;
    private final CancelGoalHandler cancelHandler;
    private final ListGoalsHandler listHandler;

    public GoalController(CreateGoalHandler createHandler, PauseGoalHandler pauseHandler,
                         ResumeGoalHandler resumeHandler, CancelGoalHandler cancelHandler, ListGoalsHandler listHandler) {
        this.createHandler = createHandler; this.pauseHandler = pauseHandler;
        this.resumeHandler = resumeHandler; this.cancelHandler = cancelHandler; this.listHandler = listHandler;
    }

    @GetMapping
    @Operation(summary = "List reading goals")
    public ResponseEntity<ApiResponse<List<ReadingGoalResponse>>> list(@AuthenticationPrincipal CurrentUser user) {
        return ResponseEntity.ok(ApiResponse.success(listHandler.handle(user.id())));
    }

    @PostMapping
    @Operation(summary = "Create reading goal")
    public ResponseEntity<ApiResponse<ReadingGoalResponse>> create(
            @AuthenticationPrincipal CurrentUser user, @Valid @RequestBody CreateGoalCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(createHandler.handle(user.id(), cmd)));
    }

    @PostMapping("/{id}/pause")
    public ResponseEntity<ApiResponse<ReadingGoalResponse>> pause(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(pauseHandler.handle(id)));
    }

    @PostMapping("/{id}/resume")
    public ResponseEntity<ApiResponse<ReadingGoalResponse>> resume(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(resumeHandler.handle(id)));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancel(@PathVariable UUID id) {
        cancelHandler.handle(id); return ResponseEntity.ok(ApiResponse.success(null));
    }
}""")

print("=== Goal done ===")

# ============================================================
# HIGHLIGHT (compact)
# ============================================================
print("=== Highlight ===")

w("highlight/domain/HighlightColor.java", "package app.vaj.highlight.domain;\npublic enum HighlightColor {\n    YELLOW, GREEN, BLUE, PINK, PURPLE, ORANGE\n}")
w("highlight/domain/HighlightStatus.java", "package app.vaj.highlight.domain;\npublic enum HighlightStatus {\n    ACTIVE, ARCHIVED, DELETED\n}")

w("highlight/domain/Highlight.java", """package app.vaj.highlight.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;
import java.time.*;
import java.util.*;

public class Highlight extends BaseAggregateRoot {
    private UUID bookId;
    private UUID userId;
    private Integer page;
    private Integer startPosition;
    private Integer endPosition;
    private String textSnapshot;
    private HighlightColor color;
    private String comment;
    private HighlightStatus status;

    private Highlight(UUID id) { super(id); }

    public static Highlight create(UUID id, UUID bookId, UUID userId, Integer page, int startPos, int endPos,
                                   String textSnapshot, HighlightColor color, String comment, Clock clock) {
        if (endPos < startPos) throw new DomainValidationException("INVALID_RANGE", List.of("End must >= start."));
        if (textSnapshot == null || textSnapshot.isBlank()) throw new DomainValidationException("EMPTY_SNAPSHOT", List.of("Text snapshot is required."));
        Highlight h = new Highlight(id);
        h.bookId = bookId; h.userId = userId; h.page = page; h.startPosition = startPos; h.endPosition = endPos;
        h.textSnapshot = textSnapshot; h.color = color != null ? color : HighlightColor.YELLOW;
        h.comment = comment; h.status = HighlightStatus.ACTIVE;
        h.markCreated(Instant.now(clock), userId);
        return h;
    }

    public void updateComment(String comment, Clock clock) {
        if (comment != null && comment.length() > 2000) throw new DomainValidationException("LONG_COMMENT", List.of("Max 2000 chars."));
        this.comment = comment; markUpdated(Instant.now(clock), userId);
    }

    public void updateColor(HighlightColor color, Clock clock) { this.color = color; markUpdated(Instant.now(clock), userId); }
    public void archive(Clock clock) { this.status = HighlightStatus.ARCHIVED; markUpdated(Instant.now(clock), userId); }
    public void delete(Clock clock) { this.status = HighlightStatus.DELETED; markDeleted(Instant.now(clock), userId); }

    public UUID getBookId() { return bookId; }
    public UUID getUserId() { return userId; }
    public Integer getPage() { return page; }
    public Integer getStartPosition() { return startPosition; }
    public Integer getEndPosition() { return endPosition; }
    public String getTextSnapshot() { return textSnapshot; }
    public HighlightColor getColor() { return color; }
    public String getComment() { return comment; }
    public HighlightStatus getStatus() { return status; }
}""")

w("highlight/domain/repository/HighlightRepository.java", """package app.vaj.highlight.domain.repository;
import app.vaj.highlight.domain.Highlight;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface HighlightRepository {
    Highlight save(Highlight h);
    Optional<Highlight> findById(UUID id);
    List<Highlight> findByBookId(UUID bookId);
}""")

w("highlight/application/command/CreateHighlightCommand.java", """package app.vaj.highlight.application.command;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
public record CreateHighlightCommand(
    @NotNull UUID bookId, Integer page, @Min(1) int startPosition, @Min(1) int endPosition,
    @NotBlank String textSnapshot, String color, String comment
) {}""")

w("highlight/application/command/UpdateHighlightCommand.java", """package app.vaj.highlight.application.command;
public record UpdateHighlightCommand(String color, String comment) {}""")

w("highlight/application/dto/HighlightResponse.java", """package app.vaj.highlight.application.dto;
import java.time.Instant;
import java.util.UUID;
public record HighlightResponse(
    UUID id, UUID bookId, Integer page, Integer startPosition, Integer endPosition,
    String textSnapshot, String color, String comment, String status, Instant createdAt, Instant updatedAt
) {}""")

w("highlight/application/handler/CreateHighlightHandler.java", """package app.vaj.highlight.application.handler;
import app.vaj.highlight.application.command.CreateHighlightCommand;
import app.vaj.highlight.application.dto.HighlightResponse;
import app.vaj.highlight.domain.*;
import app.vaj.highlight.domain.repository.HighlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;
@Service
public class CreateHighlightHandler {
    private final HighlightRepository repo; private final Clock clock;
    public CreateHighlightHandler(HighlightRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public HighlightResponse handle(UUID userId, CreateHighlightCommand cmd) {
        HighlightColor color = cmd.color() != null ? HighlightColor.valueOf(cmd.color()) : HighlightColor.YELLOW;
        Highlight h = Highlight.create(UUID.randomUUID(), cmd.bookId(), userId, cmd.page(),
                cmd.startPosition(), cmd.endPosition(), cmd.textSnapshot(), color, cmd.comment(), clock);
        repo.save(h);
        return new HighlightResponse(h.getId(), h.getBookId(), h.getPage(), h.getStartPosition(), h.getEndPosition(),
                h.getTextSnapshot(), h.getColor().name(), h.getComment(), h.getStatus().name(), h.getCreatedAt(), h.getUpdatedAt());
    }
}""")

w("highlight/application/handler/UpdateHighlightHandler.java", """package app.vaj.highlight.application.handler;
import app.vaj.highlight.application.command.UpdateHighlightCommand;
import app.vaj.highlight.application.dto.HighlightResponse;
import app.vaj.highlight.domain.*;
import app.vaj.highlight.domain.repository.HighlightRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;
@Service
public class UpdateHighlightHandler {
    private final HighlightRepository repo; private final Clock clock;
    public UpdateHighlightHandler(HighlightRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public HighlightResponse handle(UUID id, UpdateHighlightCommand cmd) {
        Highlight h = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Highlight", id));
        if (cmd.color() != null) h.updateColor(HighlightColor.valueOf(cmd.color()), clock);
        if (cmd.comment() != null) h.updateComment(cmd.comment(), clock);
        repo.save(h);
        return new HighlightResponse(h.getId(), h.getBookId(), h.getPage(), h.getStartPosition(), h.getEndPosition(),
                h.getTextSnapshot(), h.getColor().name(), h.getComment(), h.getStatus().name(), h.getCreatedAt(), h.getUpdatedAt());
    }
}""")

w("highlight/application/handler/DeleteHighlightHandler.java", """package app.vaj.highlight.application.handler;
import app.vaj.highlight.domain.Highlight;
import app.vaj.highlight.domain.repository.HighlightRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;
@Service
public class DeleteHighlightHandler {
    private final HighlightRepository repo; private final Clock clock;
    public DeleteHighlightHandler(HighlightRepository repo, Clock clock) { this.repo = repo; this.clock = clock; }
    @Transactional
    public void handle(UUID id) {
        Highlight h = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Highlight", id));
        h.delete(clock); repo.save(h);
    }
}""")

w("highlight/application/handler/ListHighlightsHandler.java", """package app.vaj.highlight.application.handler;
import app.vaj.highlight.application.dto.HighlightResponse;
import app.vaj.highlight.domain.Highlight;
import app.vaj.highlight.domain.repository.HighlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
@Service
public class ListHighlightsHandler {
    private final HighlightRepository repo;
    public ListHighlightsHandler(HighlightRepository repo) { this.repo = repo; }
    @Transactional(readOnly = true)
    public List<HighlightResponse> handle(UUID bookId) {
        return repo.findByBookId(bookId).stream()
                .map(h -> new HighlightResponse(h.getId(), h.getBookId(), h.getPage(), h.getStartPosition(), h.getEndPosition(),
                        h.getTextSnapshot(), h.getColor().name(), h.getComment(), h.getStatus().name(), h.getCreatedAt(), h.getUpdatedAt()))
                .toList();
    }
}""")

w("highlight/infrastructure/persistence/HighlightEntity.java", """package app.vaj.highlight.infrastructure.persistence;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;
@Entity
@Table(name = "Highlights")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class HighlightEntity {
    @Id @Column(name = "Id") private UUID id;
    @Column(name = "BookId", nullable = false) private UUID bookId;
    @Column(name = "UserId", nullable = false) private UUID userId;
    @Column(name = "Page") private Integer page;
    @Column(name = "StartPosition") private Integer startPosition;
    @Column(name = "EndPosition") private Integer endPosition;
    @Column(name = "TextSnapshot", columnDefinition = "NVARCHAR(MAX)", nullable = false) private String textSnapshot;
    @Column(name = "Color", nullable = false, length = 20) private String color;
    @Column(name = "Comment", length = 2000) private String comment;
    @Column(name = "Status", nullable = false, length = 50) private String status;
    @Column(name = "CreatedAt", nullable = false) private Instant createdAt;
    @Column(name = "CreatedBy") private UUID createdBy;
    @Column(name = "UpdatedAt") private Instant updatedAt;
    @Column(name = "UpdatedBy") private UUID updatedBy;
    @Column(name = "DeletedAt") private Instant deletedAt;
    @Column(name = "DeletedBy") private UUID deletedBy;
    @Column(name = "IsDeleted", nullable = false) private boolean isDeleted;
    @Version @Column(name = "Version") private Long version;
}""")

w("highlight/infrastructure/persistence/JpaHighlightRepository.java", """package app.vaj.highlight.infrastructure.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import java.util.UUID;
public interface JpaHighlightRepository extends JpaRepository<HighlightEntity, UUID> {
    Optional<HighlightEntity> findByIdAndIsDeletedFalse(UUID id);
    List<HighlightEntity> findByBookIdAndIsDeletedFalseOrderByPageAsc(UUID bookId);
}""")

w("highlight/infrastructure/persistence/HighlightRepositoryImpl.java", """package app.vaj.highlight.infrastructure.persistence;
import app.vaj.highlight.domain.*;
import app.vaj.highlight.domain.repository.HighlightRepository;
import org.springframework.stereotype.Repository;
import java.lang.reflect.Field;
import java.util.*;
@Repository
public class HighlightRepositoryImpl implements HighlightRepository {
    private final JpaHighlightRepository jpa;
    public HighlightRepositoryImpl(JpaHighlightRepository jpa) { this.jpa = jpa; }
    @Override public Highlight save(Highlight h) { jpa.save(toEntity(h)); return h; }
    @Override public Optional<Highlight> findById(UUID id) { return jpa.findByIdAndIsDeletedFalse(id).map(this::toDomain); }
    @Override public List<Highlight> findByBookId(UUID bookId) { return jpa.findByBookIdAndIsDeletedFalseOrderByPageAsc(bookId).stream().map(this::toDomain).toList(); }

    private Highlight toDomain(HighlightEntity e) {
        try {
            var c = Highlight.class.getDeclaredConstructor(UUID.class); c.setAccessible(true);
            Highlight h = c.newInstance(e.getId());
            set(h,"bookId",e.getBookId()); set(h,"userId",e.getUserId()); set(h,"page",e.getPage());
            set(h,"startPosition",e.getStartPosition()); set(h,"endPosition",e.getEndPosition());
            set(h,"textSnapshot",e.getTextSnapshot()); set(h,"color",HighlightColor.valueOf(e.getColor()));
            set(h,"comment",e.getComment()); set(h,"status",HighlightStatus.valueOf(e.getStatus()));
            set(h,"createdAt",e.getCreatedAt()); set(h,"updatedAt",e.getUpdatedAt());
            return h;
        } catch (Exception ex) { throw new RuntimeException("Mapping failed", ex); }
    }
    private HighlightEntity toEntity(Highlight h) {
        HighlightEntity e = new HighlightEntity();
        e.setId(h.getId()); e.setBookId(h.getBookId()); e.setUserId(h.getUserId()); e.setPage(h.getPage());
        e.setStartPosition(h.getStartPosition()); e.setEndPosition(h.getEndPosition());
        e.setTextSnapshot(h.getTextSnapshot()); e.setColor(h.getColor().name());
        e.setComment(h.getComment()); e.setStatus(h.getStatus().name());
        e.setCreatedAt(h.getCreatedAt()!=null?h.getCreatedAt():java.time.Instant.now());
        e.setUpdatedAt(h.getUpdatedAt()!=null?h.getUpdatedAt():java.time.Instant.now());
        e.setCreatedBy(h.getUserId()); e.setVersion(h.getVersion()!=null?h.getVersion():0L); e.setDeleted(h.isDeleted());
        return e;
    }
    private void set(Object t,String n,Object v) {
        try { Field f=t.getClass().getDeclaredField(n); f.setAccessible(true); f.set(t,v); }
        catch(Exception e) { try { Field f=t.getClass().getSuperclass().getDeclaredField(n); f.setAccessible(true); f.set(t,v); } catch(Exception ignored) {} }
    }
}""")

w("highlight/api/HighlightController.java", """package app.vaj.highlight.api;
import app.vaj.highlight.application.command.*;
import app.vaj.highlight.application.dto.HighlightResponse;
import app.vaj.highlight.application.handler.*;
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
@Tag(name = "Highlights", description = "Highlight management")
public class HighlightController {
    private final CreateHighlightHandler createHandler;
    private final UpdateHighlightHandler updateHandler;
    private final DeleteHighlightHandler deleteHandler;
    private final ListHighlightsHandler listHandler;

    public HighlightController(CreateHighlightHandler createHandler, UpdateHighlightHandler updateHandler,
                               DeleteHighlightHandler deleteHandler, ListHighlightsHandler listHandler) {
        this.createHandler = createHandler; this.updateHandler = updateHandler;
        this.deleteHandler = deleteHandler; this.listHandler = listHandler;
    }

    @GetMapping("/api/v1/books/{bookId}/highlights")
    @Operation(summary = "List highlights for a book")
    public ResponseEntity<ApiResponse<List<HighlightResponse>>> list(@PathVariable UUID bookId) {
        return ResponseEntity.ok(ApiResponse.success(listHandler.handle(bookId)));
    }

    @PostMapping("/api/v1/books/{bookId}/highlights")
    @Operation(summary = "Create a highlight")
    public ResponseEntity<ApiResponse<HighlightResponse>> create(
            @AuthenticationPrincipal CurrentUser user, @PathVariable UUID bookId,
            @Valid @RequestBody CreateHighlightCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(createHandler.handle(user.id(),
                new CreateHighlightCommand(bookId, cmd.page(), cmd.startPosition(), cmd.endPosition(), cmd.textSnapshot(), cmd.color(), cmd.comment()))));
    }

    @PatchMapping("/api/v1/highlights/{id}")
    @Operation(summary = "Update a highlight")
    public ResponseEntity<ApiResponse<HighlightResponse>> update(@PathVariable UUID id, @Valid @RequestBody UpdateHighlightCommand cmd) {
        return ResponseEntity.ok(ApiResponse.success(updateHandler.handle(id, cmd)));
    }

    @DeleteMapping("/api/v1/highlights/{id}")
    @Operation(summary = "Delete a highlight")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        deleteHandler.handle(id); return ResponseEntity.ok(ApiResponse.success(null));
    }
}""")

print("=== Highlight done ===")
print("All done!")