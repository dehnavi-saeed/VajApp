package app.vaj.highlight.domain;

import app.vaj.common.domain.BaseAggregateRoot;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

public class Highlight extends BaseAggregateRoot {

    private UUID bookId;
    private UUID readingSessionId;
    private String content;
    private Integer pageNumber;
    private String chapter;
    private String color;

    private Highlight(UUID id) { super(id); }

    public static Highlight create(UUID id, UUID bookId, String content, Integer pageNumber,
                                    String chapter, String color, UUID userId, Clock clock) {
        if (content == null || content.isBlank()) {
            throw new app.vaj.common.domain.DomainValidationException(
                "INVALID_HIGHLIGHT", java.util.List.of("Highlight content is required."));
        }
        Highlight h = new Highlight(id);
        h.bookId = bookId;
        h.readingSessionId = null;
        h.content = content;
        h.pageNumber = pageNumber;
        h.chapter = chapter;
        h.color = color != null ? color : "YELLOW";
        h.markCreated(Instant.now(clock), userId);
        return h;
    }

    public void updateContent(String content, UUID userId, Clock clock) {
        if (content != null) this.content = content;
        markUpdated(Instant.now(clock), userId);
    }

    public UUID getBookId() { return bookId; }
    public String getContent() { return content; }
    public Integer getPageNumber() { return pageNumber; }
    public String getChapter() { return chapter; }
    public String getColor() { return color; }
}