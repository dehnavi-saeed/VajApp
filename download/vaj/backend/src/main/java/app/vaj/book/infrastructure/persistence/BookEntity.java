package app.vaj.book.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "Books")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BookEntity {

    @Id @Column(name = "Id") private UUID id;
    @Column(name = "LibraryId", nullable = false) private UUID libraryId;
    @Column(name = "Title", nullable = false, length = 500) private String title;
    @Column(name = "Subtitle", length = 500) private String subtitle;
    @Column(name = "ISBN", length = 20) private String isbn;
    @Column(name = "Description") @ColumnDefault("NULL") private String description;
    @Column(name = "Language", nullable = false, length = 10) private String language;
    @Column(name = "PageCount") private Integer pageCount;
    @Column(name = "Format", nullable = false, length = 20) @Enumerated(EnumType.STRING) private FormatJpa format;
    @Column(name = "CoverUrl", length = 500) private String coverUrl;
    @Column(name = "PublisherId") private UUID publisherId;
    @Column(name = "Status", nullable = false, length = 20) @Enumerated(EnumType.STRING) private StatusJpa status;
    @Column(name = "CreatedAt", nullable = false) private Instant createdAt;
    @Column(name = "CreatedBy") private UUID createdBy;
    @Column(name = "UpdatedAt", nullable = false) private Instant updatedAt;
    @Column(name = "UpdatedBy") private UUID updatedBy;
    @Column(name = "DeletedAt") private Instant deletedAt;
    @Column(name = "DeletedBy") private UUID deletedBy;
    @Column(name = "IsDeleted", nullable = false) private boolean isDeleted;
    @Version @Column(name = "Version") private Long version;

    public enum FormatJpa { PHYSICAL, EBOOK, AUDIOBOOK, PDF }
    public enum StatusJpa { DRAFT, UNREAD, READING, COMPLETED, ARCHIVED, DELETED }
}