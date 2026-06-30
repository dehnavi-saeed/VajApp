package app.vaj.tag.infrastructure.persistence;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;
@Entity
@Table(name = "Tags")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TagEntity {
    @Id @Column(name = "Id") private UUID id;
    @Column(name = "UserId", nullable = false) private UUID userId;
    @Column(name = "Name", nullable = false, length = 100) private String name;
    @Column(name = "Color", length = 7) private String color;
    @Column(name = "Description", length = 500) private String description;
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