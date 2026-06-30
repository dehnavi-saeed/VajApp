package app.vaj.statistics.application.handler;

import app.vaj.statistics.application.dto.StatisticsResponse;
import app.vaj.statistics.infrastructure.persistence.JpaReadingStatisticsRepository;
import app.vaj.statistics.infrastructure.persistence.ReadingStatisticsEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class GetStatisticsHandler {

    private final JpaReadingStatisticsRepository statsRepo;

    public GetStatisticsHandler(JpaReadingStatisticsRepository statsRepo) {
        this.statsRepo = statsRepo;
    }

    @Transactional(readOnly = true)
    public StatisticsResponse handle(UUID userId) {
        List<ReadingStatisticsEntity> allStats = statsRepo.findByUserIdOrderByStatDateDesc(userId);

        long totalBooksRead = 0;
        long totalPagesRead = 0;
        long totalMinutesRead = 0;
        long totalHighlights = 0;
        long totalNotes = 0;

        for (ReadingStatisticsEntity stat : allStats) {
            totalBooksRead += stat.getBooksCompleted();
            totalPagesRead += stat.getPagesRead();
            totalMinutesRead += stat.getMinutesRead();
            totalHighlights += stat.getHighlightsCreated();
            totalNotes += stat.getNotesCreated();
        }

        long currentStreak = calculateStreak(allStats);

        double averageDailyPages = allStats.isEmpty() ? 0.0
                : (double) totalPagesRead / allStats.size();

        return new StatisticsResponse(totalBooksRead, totalPagesRead, totalMinutesRead,
                totalHighlights, totalNotes, currentStreak, Math.round(averageDailyPages * 100.0) / 100.0);
    }

    private long calculateStreak(List<ReadingStatisticsEntity> stats) {
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