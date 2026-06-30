package app.vaj.note.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;
import java.time.*;
import java.util.*;

public class KnowledgeNote extends BaseAggregateRoot {
    private UUID userId;
    private String title;
    private String content;
    private NoteStatus status;

    private KnowledgeNote(UUID id) { super(id); }

    public static KnowledgeNote create(UUID id, UUID userId, String title, String content, Clock clock) {
        if (content == null || content.isBlank()) throw new DomainValidationException("EMPTY_CONTENT", List.of("Content is required."));
        if (title != null && title.length() > 300) throw new DomainValidationException("LONG_TITLE", List.of("Title max 300 chars."));
        KnowledgeNote n = new KnowledgeNote(id);
        n.userId = userId; n.title = title; n.content = content; n.status = NoteStatus.DRAFT;
        n.markCreated(Instant.now(clock), userId);
        return n;
    }

    public void update(String title, String content, Clock clock) {
        if (title != null) { if (title.length() > 300) throw new DomainValidationException("LONG_TITLE", List.of("Max 300.")); this.title = title; }
        if (content != null && !content.isBlank()) this.content = content;
        markUpdated(Instant.now(clock), userId);
    }

    public void publish(Clock clock) { this.status = NoteStatus.PUBLISHED; markUpdated(Instant.now(clock), userId); }
    public void archive(Clock clock) { this.status = NoteStatus.ARCHIVED; markUpdated(Instant.now(clock), userId); }
    public void delete(Clock clock) { this.status = NoteStatus.DELETED; markDeleted(Instant.now(clock), userId); }

    public UUID getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public NoteStatus getStatus() { return status; }
}