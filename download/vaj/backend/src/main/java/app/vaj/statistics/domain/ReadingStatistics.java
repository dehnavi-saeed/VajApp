package app.vaj.statistics.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class ReadingStatistics {

    private final UUID id;
    private final UUID userId;
    private UUID libraryId;
    private final LocalDate statDate;
    private int pagesRead;
    private int minutesRead;
    private int booksCompleted;
    private int sessionsCount;
    private int notesCreated;
    private int highlightsCreated;
    private Instant createdAt;

    private ReadingStatistics(UUID id, UUID userId, UUID libraryId, LocalDate statDate) {
        this.id = id;
        this.userId = userId;
        this.libraryId = libraryId;
        this.statDate = statDate;
        this.createdAt = Instant.now();
    }

    public static ReadingStatistics create(UUID id, UUID userId, UUID libraryId, LocalDate statDate) {
        return new ReadingStatistics(id, userId, libraryId, statDate);
    }

    public void incrementPages(int pages) {
        if (pages > 0) this.pagesRead += pages;
    }

    public void incrementMinutes(int minutes) {
        if (minutes > 0) this.minutesRead += minutes;
    }

    public void incrementBooksCompleted() {
        this.booksCompleted++;
    }

    public void incrementSessionsCount() {
        this.sessionsCount++;
    }

    public void incrementNotesCreated() {
        this.notesCreated++;
    }

    public void incrementHighlightsCreated() {
        this.highlightsCreated++;
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public UUID getLibraryId() { return libraryId; }
    public LocalDate getStatDate() { return statDate; }
    public int getPagesRead() { return pagesRead; }
    public int getMinutesRead() { return minutesRead; }
    public int getBooksCompleted() { return booksCompleted; }
    public int getSessionsCount() { return sessionsCount; }
    public int getNotesCreated() { return notesCreated; }
    public int getHighlightsCreated() { return highlightsCreated; }
    public Instant getCreatedAt() { return createdAt; }

    public void setLibraryId(UUID libraryId) { this.libraryId = libraryId; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}