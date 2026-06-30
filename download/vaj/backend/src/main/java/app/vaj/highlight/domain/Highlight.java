package app.vaj.highlight.domain;

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
}