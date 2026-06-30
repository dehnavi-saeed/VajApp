package app.vaj.bookmark.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "Bookmarks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BookmarkEntity {
    @Id @Column(name = "Id") private UUID id;
    @Column(name = "BookId", nullable = false) private UUID bookId;
    @Column(name = "UserId", nullable = false) private UUID userId;
    @Column(name = "Page") private Integer page;
    @Column(name = "Chapter", length = 200) private String chapter;
    @Column(name = "Title", length = 200) private String title;
    @Column(name = "Description", length = 1000) private String description;
    @Column(name = "Color", length = 20) private String color;
    @Column(name = "SortOrder", nullable = false) private int sortOrder;
    @Column(name = "CreatedAt", nullable = false) private Instant createdAt;
    @Column(name = "CreatedBy") private UUID createdBy;
    @Column(name = "UpdatedAt") private Instant updatedAt;
    @Column(name = "DeletedAt") private Instant deletedAt;
    @Column(name = "DeletedBy") private UUID deletedBy;
    @Column(name = "IsDeleted", nullable = false) private boolean isDeleted;
    @Version @Column(name = "Version") private Long version;
}