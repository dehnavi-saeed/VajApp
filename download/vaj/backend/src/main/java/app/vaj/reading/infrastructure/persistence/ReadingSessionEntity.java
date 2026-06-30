package app.vaj.reading.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "ReadingSessions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReadingSessionEntity {
    @Id @Column(name = "Id") private UUID id;
    @Column(name = "BookId", nullable = false) private UUID bookId;
    @Column(name = "UserId", nullable = false) private UUID userId;
    @Column(name = "StartPage") private Integer startPage;
    @Column(name = "EndPage") private Integer endPage;
    @Column(name = "DurationMinutes") private Integer durationMinutes;
    @Column(name = "State", nullable = false, length = 20) @Enumerated(EnumType.STRING) private StateJpa state;
    @Column(name = "CreatedAt", nullable = false) private Instant createdAt;
    @Column(name = "CreatedBy") private UUID createdBy;
    @Column(name = "UpdatedAt") private Instant updatedAt;
    @Column(name = "UpdatedBy") private UUID updatedBy;
    @Column(name = "DeletedAt") private Instant deletedAt;
    @Column(name = "DeletedBy") private UUID deletedBy;
    @Column(name = "IsDeleted", nullable = false) private boolean isDeleted;
    @Version @Column(name = "Version") private Long version;

    public enum StateJpa { STARTED, PAUSED, COMPLETED, CANCELLED }
}