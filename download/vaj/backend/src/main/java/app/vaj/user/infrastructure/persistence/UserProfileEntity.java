package app.vaj.user.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "UserProfiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileEntity {

    @Id
    @Column(name = "Id")
    private UUID id;

    @Column(name = "UserId", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "DisplayName", length = 100)
    private String displayName;

    @Column(name = "Bio", length = 500)
    private String bio;

    @Column(name = "AvatarUrl", length = 500)
    private String avatarUrl;

    @Column(name = "CreatedAt", nullable = false)
    private Instant createdAt;

    @Column(name = "UpdatedAt", nullable = false)
    private Instant updatedAt;
}