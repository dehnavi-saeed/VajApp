package app.vaj.statistics.domain.repository;

import app.vaj.statistics.domain.ReadingStatistics;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadingStatisticsRepository {

    ReadingStatistics save(ReadingStatistics statistics);

    Optional<ReadingStatistics> findByUserIdAndStatDate(UUID userId, LocalDate statDate);

    List<ReadingStatistics> findByUserIdAndStatDateBetween(UUID userId, LocalDate from, LocalDate to);

    List<ReadingStatistics> findByUserIdOrderByStatDateDesc(UUID userId);
}