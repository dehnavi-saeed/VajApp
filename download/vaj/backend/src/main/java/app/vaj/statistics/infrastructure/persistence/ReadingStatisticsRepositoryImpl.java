package app.vaj.statistics.infrastructure.persistence;

import app.vaj.statistics.domain.ReadingStatistics;
import app.vaj.statistics.domain.repository.ReadingStatisticsRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ReadingStatisticsRepositoryImpl implements ReadingStatisticsRepository {

    private final JpaReadingStatisticsRepository jpa;

    public ReadingStatisticsRepositoryImpl(JpaReadingStatisticsRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public ReadingStatistics save(ReadingStatistics statistics) {
        jpa.save(toEntity(statistics));
        return statistics;
    }

    @Override
    public Optional<ReadingStatistics> findByUserIdAndStatDate(UUID userId, LocalDate statDate) {
        return jpa.findByUserIdAndStatDate(userId, statDate).map(this::toDomain);
    }

    @Override
    public List<ReadingStatistics> findByUserIdAndStatDateBetween(UUID userId, LocalDate from, LocalDate to) {
        return jpa.findByUserIdAndStatDateBetweenOrderByStatDateAsc(userId, from, to).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<ReadingStatistics> findByUserIdOrderByStatDateDesc(UUID userId) {
        return jpa.findByUserIdOrderByStatDateDesc(userId).stream()
                .map(this::toDomain)
                .toList();
    }

    private ReadingStatistics toDomain(ReadingStatisticsEntity e) {
        ReadingStatistics stats = ReadingStatistics.create(e.getId(), e.getUserId(), e.getLibraryId(), e.getStatDate());
        stats.incrementPages(e.getPagesRead());
        stats.incrementMinutes(e.getMinutesRead());
        for (int i = 0; i < e.getBooksCompleted(); i++) stats.incrementBooksCompleted();
        for (int i = 0; i < e.getSessionsCount(); i++) stats.incrementSessionsCount();
        for (int i = 0; i < e.getNotesCreated(); i++) stats.incrementNotesCreated();
        for (int i = 0; i < e.getHighlightsCreated(); i++) stats.incrementHighlightsCreated();
        stats.setCreatedAt(e.getCreatedAt());
        return stats;
    }

    private ReadingStatisticsEntity toEntity(ReadingStatistics s) {
        ReadingStatisticsEntity e = new ReadingStatisticsEntity();
        e.setId(s.getId());
        e.setUserId(s.getUserId());
        e.setLibraryId(s.getLibraryId());
        e.setStatDate(s.getStatDate());
        e.setPagesRead(s.getPagesRead());
        e.setMinutesRead(s.getMinutesRead());
        e.setBooksCompleted(s.getBooksCompleted());
        e.setSessionsCount(s.getSessionsCount());
        e.setNotesCreated(s.getNotesCreated());
        e.setHighlightsCreated(s.getHighlightsCreated());
        e.setCreatedAt(s.getCreatedAt() != null ? s.getCreatedAt() : java.time.Instant.now());
        return e;
    }
}