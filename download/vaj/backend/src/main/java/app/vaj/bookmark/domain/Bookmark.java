package app.vaj.bookmark.domain;

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
}