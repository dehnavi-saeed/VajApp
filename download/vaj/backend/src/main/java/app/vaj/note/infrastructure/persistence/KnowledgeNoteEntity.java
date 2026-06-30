package app.vaj.note.infrastructure.persistence;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;
@Entity
@Table(name = "KnowledgeNotes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class KnowledgeNoteEntity {
    @Id @Column(name = "Id") private UUID id;
    @Column(name = "UserId", nullable = false) private UUID userId;
    @Column(name = "Title", length = 300) private String title;
    @Column(name = "Content", columnDefinition = "NVARCHAR(MAX)", nullable = false) private String content;
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