package app.vaj.book.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BookAuthors")
@IdClass(BookAuthorEntity.BookAuthorId.class)
public class BookAuthorEntity {

    @Id
    @Column(name = "BookId")
    private UUID bookId;

    @Id
    @Column(name = "AuthorId")
    private UUID authorId;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookAuthorId implements Serializable {
        private UUID bookId;
        private UUID authorId;
    }
}