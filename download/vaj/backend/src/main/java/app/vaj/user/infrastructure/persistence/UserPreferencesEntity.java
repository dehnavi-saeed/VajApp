package app.vaj.user.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "UserPreferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferencesEntity {

    @Id
    @Column(name = "Id")
    private UUID id;

    @Column(name = "UserId", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "Language", nullable = false, length = 10)
    private String language;

    @Column(name = "Timezone", nullable = false, length = 50)
    private String timezone;

    @Column(name = "Theme", nullable = false, length = 20)
    private String theme;

    @Column(name = "CreatedAt", nullable = false)
    private Instant createdAt;

    @Column(name = "UpdatedAt", nullable = false)
    private Instant updatedAt;
}