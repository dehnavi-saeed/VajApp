package app.vaj.readingstate.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "ReadingStates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadingStateEntity {

    @Id
    @Column(name = "Id")
    private UUID id;

    @Column(name = "BookId", nullable = false)
    private UUID bookId;

    @Column(name = "UserId", nullable = false)
    private UUID userId;

    @Column(name = "CurrentPage", nullable = false)
    private int currentPage;

    @Column(name = "TotalPagesRead", nullable = false)
    private int totalPagesRead;

    @Column(name = "ProgressPercentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal progressPercentage;

    @Column(name = "TotalReadingMinutes", nullable = false)
    private int totalReadingMinutes;

    @Column(name = "LastSessionId")
    private UUID lastSessionId;

    @Column(name = "LastReadAt")
    private Instant lastReadAt;

    @Column(name = "EstimatedFinishDate")
    private Instant estimatedFinishDate;

    @Column(name = "Status", nullable = false, length = 20)
    private String status;

    @Column(name = "UpdatedAt", nullable = false)
    private Instant updatedAt;
}