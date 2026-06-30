package app.vaj.book.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Book extends BaseAggregateRoot {

    private UUID libraryId;
    private String title;
    private String subtitle;
    private String isbn;
    private String description;
    private String language;
    private Integer pageCount;
    private BookFormat format;
    private String coverUrl;
    private ReadingStatus status;

    private Book(UUID id) {
        super(id);
    }

    public static Book create(UUID id, UUID libraryId, String title, String isbn,
                              String language, Integer pageCount, BookFormat format, Clock clock) {
        validateTitle(title);
        Book book = new Book(id);
        book.libraryId = libraryId;
        book.title = title;
        book.isbn = isbn;
        book.language = language != null ? language : "en";
        book.pageCount = pageCount;
        book.format = format != null ? format : BookFormat.PHYSICAL;
        book.status = ReadingStatus.UNREAD;
        book.markCreated(Instant.now(clock), null);
        return book;
    }

    public void updateMetadata(String title, String subtitle, String description,
                               String language, Integer pageCount, UUID userId, Clock clock) {
        if (title != null) validateTitle(title);
        if (title != null) this.title = title;
        if (subtitle != null) this.subtitle = subtitle;
        if (description != null) this.description = description;
        if (language != null) this.language = language;
        if (pageCount != null && pageCount > 0) this.pageCount = pageCount;
        markUpdated(Instant.now(clock), userId);
    }

    public void updateCover(String coverUrl, UUID userId, Clock clock) {
        this.coverUrl = coverUrl;
        markUpdated(Instant.now(clock), userId);
    }

    public void startReading(Clock clock) {
        if (status == ReadingStatus.COMPLETED || status == ReadingStatus.DELETED) {
            status = ReadingStatus.READING;
        } else {
            status = ReadingStatus.READING;
        }
        markUpdated(Instant.now(clock), getId());
    }

    public void completeReading(Clock clock) {
        status = ReadingStatus.COMPLETED;
        markUpdated(Instant.now(clock), getId());
    }

    public void archive(Clock clock) {
        if (status != ReadingStatus.DELETED) {
            status = ReadingStatus.ARCHIVED;
            markUpdated(Instant.now(clock), getId());
        }
    }

    public void restore(Clock clock) {
        if (status == ReadingStatus.ARCHIVED) {
            status = ReadingStatus.UNREAD;
            markUpdated(Instant.now(clock), getId());
        }
    }

    public void delete(Clock clock) {
        status = ReadingStatus.DELETED;
        markDeleted(Instant.now(clock), getId());
    }

    private static void validateTitle(String title) {
        List<String> errors = new ArrayList<>();
        if (title == null || title.isBlank()) errors.add("Title is required.");
        else if (title.length() < 3 || title.length() > 500) errors.add("Title must be 3-500 characters.");
        if (!errors.isEmpty()) throw new DomainValidationException("INVALID_BOOK_TITLE", errors);
    }

    public UUID getLibraryId() { return libraryId; }
    public String getTitle() { return title; }
    public String getSubtitle() { return subtitle; }
    public String getIsbn() { return isbn; }
    public String getDescription() { return description; }
    public String getLanguage() { return language; }
    public Integer getPageCount() { return pageCount; }
    public BookFormat getFormat() { return format; }
    public String getCoverUrl() { return coverUrl; }
    public ReadingStatus getStatus() { return status; }
}