package app.vaj.book.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaBookPublisherRepository extends JpaRepository<BookPublisherEntity, BookPublisherEntity.BookPublisherId> {
    List<BookPublisherEntity> findByBookId(UUID bookId);
    Optional<BookPublisherEntity> findByBookIdAndPublisherId(UUID bookId, UUID publisherId);
    void deleteByBookId(UUID bookId);
}