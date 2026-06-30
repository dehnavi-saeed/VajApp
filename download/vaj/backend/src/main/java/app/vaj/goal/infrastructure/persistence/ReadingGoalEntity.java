package app.vaj.goal.infrastructure.persistence;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;
@Entity
@Table(name = "ReadingGoals")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReadingGoalEntity {
    @Id @Column(name = "Id") private UUID id;
    @Column(name = "UserId", nullable = false) private UUID userId;
    @Column(name = "GoalType", nullable = false, length = 50) @Enumerated(EnumType.STRING) private TypeJpa goalType;
    @Column(name = "Target", nullable = false) private int target;
    @Column(name = "CurrentProgress", nullable = false) private int currentProgress;
    @Column(name = "StartDate", nullable = false) private Instant startDate;
    @Column(name = "EndDate") private Instant endDate;
    @Column(name = "Status", nullable = false, length = 50) @Enumerated(EnumType.STRING) private StatusJpa status;
    @Column(name = "CreatedAt", nullable = false) private Instant createdAt;
    @Column(name = "CreatedBy") private UUID createdBy;
    @Column(name = "UpdatedAt") private Instant updatedAt;
    @Column(name = "UpdatedBy") private UUID updatedBy;
    @Column(name = "DeletedAt") private Instant deletedAt;
    @Column(name = "DeletedBy") private UUID deletedBy;
    @Column(name = "IsDeleted", nullable = false) private boolean isDeleted;
    @Version @Column(name = "Version") private Long version;
    public enum TypeJpa { DAILY_PAGES, DAILY_MINUTES, WEEKLY_PAGES, MONTHLY_BOOKS, READING_STREAK }
    public enum StatusJpa { DRAFT, ACTIVE, PAUSED, COMPLETED, CANCELLED, ARCHIVED }
}