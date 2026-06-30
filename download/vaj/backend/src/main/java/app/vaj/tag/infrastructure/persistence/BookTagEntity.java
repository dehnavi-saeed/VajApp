package app.vaj.tag.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "BookTags")
@IdClass(BookTagEntity.BookTagId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookTagEntity {

    @Id
    @Column(name = "BookId")
    private UUID bookId;

    @Id
    @Column(name = "TagId")
    private UUID tagId;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookTagId implements Serializable {
        private UUID bookId;
        private UUID tagId;
    }
}