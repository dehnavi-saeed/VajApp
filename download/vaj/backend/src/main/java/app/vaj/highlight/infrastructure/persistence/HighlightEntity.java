package app.vaj.highlight.infrastructure.persistence;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;
@Entity
@Table(name = "Highlights")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class HighlightEntity {
    @Id @Column(name = "Id") private UUID id;
    @Column(name = "BookId", nullable = false) private UUID bookId;
    @Column(name = "UserId", nullable = false) private UUID userId;
    @Column(name = "Page") private Integer page;
    @Column(name = "StartPosition") private Integer startPosition;
    @Column(name = "EndPosition") private Integer endPosition;
    @Column(name = "TextSnapshot", columnDefinition = "NVARCHAR(MAX)", nullable = false) private String textSnapshot;
    @Column(name = "Color", nullable = false, length = 20) private String color;
    @Column(name = "Comment", length = 2000) private String comment;
    @Column(name = "Status", nullable = false, length = 50) private String status;
    @Column(name = "CreatedAt", nullable = false) private Instant createdAt;
    @Column(name = "CreatedBy") private UUID createdBy;
    @Column(name = "UpdatedAt") private Instant updatedAt;
    @Column(name = "UpdatedBy") private UUID updatedBy;
    @Column(name = "DeletedAt") private Instant deletedAt;
    @Column(name = "DeletedBy") private UUID deletedBy;
    @Column(name = "IsDeleted", nullable = false) private boolean isDeleted;
    @Version @Column(name = "Version") private Long version;
}