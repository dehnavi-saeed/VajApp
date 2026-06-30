package app.vaj.statistics.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "ReadingStatistics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadingStatisticsEntity {

    @Id
    @Column(name = "Id")
    private UUID id;

    @Column(name = "UserId", nullable = false)
    private UUID userId;

    @Column(name = "LibraryId")
    private UUID libraryId;

    @Column(name = "StatDate", nullable = false)
    private LocalDate statDate;

    @Column(name = "PagesRead", nullable = false)
    private int pagesRead;

    @Column(name = "MinutesRead", nullable = false)
    private int minutesRead;

    @Column(name = "BooksCompleted", nullable = false)
    private int booksCompleted;

    @Column(name = "SessionsCount", nullable = false)
    private int sessionsCount;

    @Column(name = "NotesCreated", nullable = false)
    private int notesCreated;

    @Column(name = "HighlightsCreated", nullable = false)
    private int highlightsCreated;

    @Column(name = "CreatedAt", nullable = false)
    private Instant createdAt;
}