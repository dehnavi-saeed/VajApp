package app.vaj.publisher.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "Publishers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PublisherEntity {

    @Id @Column(name = "Id") private UUID id;
    @Column(name = "Name", nullable = false, length = 200) private String name;
    @Column(name = "Website", length = 500) private String website;
    @Column(name = "Country", length = 10) private String country;
    @Column(name = "Status", nullable = false, length = 50) @Enumerated(EnumType.STRING) private StatusJpa status;
    @Column(name = "CreatedAt", nullable = false) private Instant createdAt;
    @Column(name = "CreatedBy") private UUID createdBy;
    @Column(name = "UpdatedAt") private Instant updatedAt;
    @Column(name = "UpdatedBy") private UUID updatedBy;
    @Column(name = "DeletedAt") private Instant deletedAt;
    @Column(name = "DeletedBy") private UUID deletedBy;
    @Column(name = "IsDeleted", nullable = false) private boolean isDeleted;
    @Version @Column(name = "Version") private Long version;

    public enum StatusJpa { ACTIVE, ARCHIVED, DELETED }
}