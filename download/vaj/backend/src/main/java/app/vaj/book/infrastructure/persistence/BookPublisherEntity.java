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
@Table(name = "BookPublishers")
@IdClass(BookPublisherEntity.BookPublisherId.class)
public class BookPublisherEntity {

    @Id
    @Column(name = "BookId")
    private UUID bookId;

    @Id
    @Column(name = "PublisherId")
    private UUID publisherId;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookPublisherId implements Serializable {
        private UUID bookId;
        private UUID publisherId;
    }
}