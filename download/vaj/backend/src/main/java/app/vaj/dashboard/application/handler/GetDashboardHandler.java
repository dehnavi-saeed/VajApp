package app.vaj.dashboard.application.handler;

import app.vaj.book.infrastructure.persistence.BookEntity;
import app.vaj.book.infrastructure.persistence.JpaBookRepository;
import app.vaj.bookmark.infrastructure.persistence.JpaBookmarkRepository;
import app.vaj.dashboard.application.dto.DashboardResponse;
import app.vaj.goal.infrastructure.persistence.JpaReadingGoalRepository;
import app.vaj.goal.infrastructure.persistence.ReadingGoalEntity;
import app.vaj.highlight.infrastructure.persistence.JpaHighlightRepository;
import app.vaj.library.infrastructure.persistence.JpaLibraryRepository;
import app.vaj.library.infrastructure.persistence.LibraryEntity;
import app.vaj.note.infrastructure.persistence.JpaKnowledgeNoteRepository;
import app.vaj.statistics.infrastructure.persistence.JpaReadingStatisticsRepository;
import app.vaj.statistics.infrastructure.persistence.ReadingStatisticsEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class GetDashboardHandler {

    private final JpaLibraryRepository libraryRepo;
    private final JpaBookRepository bookRepo;
    private final JpaKnowledgeNoteRepository noteRepo;
    private final JpaHighlightRepository highlightRepo;
    private final JpaBookmarkRepository bookmarkRepo;
    private final JpaReadingGoalRepository goalRepo;
    private final JpaReadingStatisticsRepository statsRepo;

    public GetDashboardHandler(JpaLibraryRepository libraryRepo,
                               JpaBookRepository bookRepo,
                               JpaKnowledgeNoteRepository noteRepo,
                               JpaHighlightRepository highlightRepo,
                               JpaBookmarkRepository bookmarkRepo,
                               JpaReadingGoalRepository goalRepo,
                               JpaReadingStatisticsRepository statsRepo) {
        this.libraryRepo = libraryRepo;
        this.bookRepo = bookRepo;
        this.noteRepo = noteRepo;
        this.highlightRepo = highlightRepo;
        this.bookmarkRepo = bookmarkRepo;
        this.goalRepo = goalRepo;
        this.statsRepo = statsRepo;
    }

    @Transactional(readOnly = true)
    public DashboardResponse handle(UUID userId) {
        List<UUID> libraryIds = libraryRepo.findByUserIdAndIsDeletedFalse(userId).stream()
                .map(LibraryEntity::getId)
                .toList();

        long totalBooks = libraryIds.isEmpty() ? 0 : bookRepo.countByLibraryIdInAndIsDeletedFalse(libraryIds);
        long currentlyReading = libraryIds.isEmpty() ? 0 : bookRepo.countByLibraryIdInAndStatusAndIsDeletedFalse(libraryIds, "READING");
        long completedBooks = libraryIds.isEmpty() ? 0 : bookRepo.countByLibraryIdInAndStatusAndIsDeletedFalse(libraryIds, "COMPLETED");
        long totalNotes = noteRepo.countByUserIdAndIsDeletedFalse(userId);
        long totalHighlights = highlightRepo.countByUserIdAndIsDeletedFalse(userId);
        long totalBookmarks = bookmarkRepo.countByUserIdAndIsDeletedFalse(userId);
        long activeGoals = goalRepo.countByUserIdAndStatusAndIsDeletedFalse(userId, ReadingGoalEntity.StatusJpa.ACTIVE);
        long readingStreak = calculateStreak(userId);

        List<DashboardResponse.RecentlyReadBook> recentlyRead = libraryIds.isEmpty()
                ? List.of()
                : bookRepo.findByLibraryIdInAndStatusInAndIsDeletedFalseOrderByUpdatedAtDesc(
                        libraryIds, List.of("READING", "COMPLETED"), PageRequest.of(0, 5))
                .stream()
                .map(b -> new DashboardResponse.RecentlyReadBook(b.getId(), b.getTitle(), b.getStatus().name()))
                .toList();

        return new DashboardResponse(totalBooks, currentlyReading, completedBooks,
                totalNotes, totalHighlights, totalBookmarks, activeGoals, readingStreak, recentlyRead);
    }

    private long calculateStreak(UUID userId) {
        List<ReadingStatisticsEntity> stats = statsRepo.findByUserIdOrderByStatDateDesc(userId);
        if (stats.isEmpty()) return 0;

        LocalDate today = LocalDate.now();
        LocalDate expectedDate = today;

        long streak = 0;
        for (ReadingStatisticsEntity stat : stats) {
            if (stat.getStatDate().equals(expectedDate)) {
                streak++;
                expectedDate = expectedDate.minusDays(1);
            } else if (stat.getStatDate().isBefore(expectedDate)) {
                break;
            }
        }
        return streak;
    }
}