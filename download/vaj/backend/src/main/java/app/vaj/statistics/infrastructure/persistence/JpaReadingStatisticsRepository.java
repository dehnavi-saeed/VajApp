package app.vaj.statistics.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaReadingStatisticsRepository extends JpaRepository<ReadingStatisticsEntity, UUID> {

    Optional<ReadingStatisticsEntity> findByUserIdAndStatDate(UUID userId, LocalDate statDate);

    List<ReadingStatisticsEntity> findByUserIdAndStatDateBetweenOrderByStatDateAsc(UUID userId, LocalDate from, LocalDate to);

    List<ReadingStatisticsEntity> findByUserIdOrderByStatDateDesc(UUID userId);
}