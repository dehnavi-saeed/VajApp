package app.vaj.auth.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SoftDelete(type = SoftDeleteType.HARD, columnName = "IsDeleted")
public class UserAuthEntity {

    @Id
    @Column(name = "Id")
    private UUID id;

    @Column(name = "Username", nullable = false, length = 100)
    private String username;

    @Column(name = "Email", nullable = false, length = 255)
    private String email;

    @Column(name = "PasswordHash", nullable = false, length = 500)
    private String passwordHash;

    @Column(name = "Status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private UserStatusJpa status;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
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

    public enum UserStatusJpa {
        PENDING, ACTIVE, SUSPENDED, DELETED
    }
}