package app.vaj.note.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KnowledgeNote extends BaseAggregateRoot {

    private UUID bookId;
    private String title;
    private String content;
    private String type;

    private KnowledgeNote(UUID id) { super(id); }

    public static KnowledgeNote create(UUID id, UUID bookId, String title, String content,
                                        String type, UUID userId, Clock clock) {
        List<String> errors = new ArrayList<>();
        if (title == null || title.isBlank()) errors.add("Title is required.");
        if (content == null || content.isBlank()) errors.add("Content is required.");
        if (!errors.isEmpty()) throw new DomainValidationException("INVALID_NOTE", errors);

        KnowledgeNote note = new KnowledgeNote(id);
        note.bookId = bookId;
        note.title = title;
        note.content = content;
        note.type = type != null ? type : "GENERAL";
        note.markCreated(Instant.now(clock), userId);
        return note;
    }

    public void update(String title, String content, UUID userId, Clock clock) {
        if (title != null) this.title = title;
        if (content != null) this.content = content;
        markUpdated(Instant.now(clock), userId);
    }

    public UUID getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getType() { return type; }
}