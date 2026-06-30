package app.vaj.author.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "Authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorEntity {

    @Id
    @Column(name = "Id")
    private UUID id;

    @Column(name = "Name", nullable = false, length = 200)
    private String name;

    @Column(name = "Bio", length = 5000)
    private String bio;

    @Column(name = "Type", nullable = false, length = 50)
    private String type;

    @Column(name = "Status", nullable = false, length = 50)
    private String status;

    @Column(name = "CreatedAt", nullable = false)
    private Instant createdAt;

    @Column(name = "UpdatedAt")
    private Instant updatedAt;

    @Column(name = "DeletedAt")
    private Instant deletedAt;

    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted;

    @Version
    @Column(name = "Version")
    private Long version;
}