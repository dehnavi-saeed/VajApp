package app.vaj.category.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "BookCategories")
@IdClass(BookCategoryEntity.BookCategoryId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCategoryEntity {

    @Id
    @Column(name = "BookId")
    private UUID bookId;

    @Id
    @Column(name = "CategoryId")
    private UUID categoryId;

    @Column(name = "AssignedAt", nullable = false)
    private java.time.Instant assignedAt;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookCategoryId implements Serializable {
        private UUID bookId;
        private UUID categoryId;
    }
}