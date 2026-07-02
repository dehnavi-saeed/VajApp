package app.vaj.category.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "Categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

    @Id
    @Column(name = "Id")
    private UUID id;

    @Column(name = "Name", nullable = false, length = 100)
    private String name;

    @Column(name = "Description", length = 1000)
    private String description;

    @Column(name = "DisplayOrder", nullable = false)
    private int displayOrder;

    @Column(name = "Status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private StatusJpa status;

    @Column(name = "CreatedAt", nullable = false)
    private Instant createdAt;

    @Column(name = "CreatedBy")
    private UUID createdBy;

    @Column(name = "UpdatedAt", nullable = false)
    private Instant updatedAt;

    @Column(name = "UpdatedBy")
    private UUID updatedBy;

    @Column(name = "DeletedAt")
    private Instant deletedAt;

    @Column(name = "DeletedBy")
    private UUID deletedBy;

    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted;

    @Version
    @Column(name = "Version")
    private Long version;

    public enum StatusJpa {
        ACTIVE, INACTIVE, DELETED
    }
}