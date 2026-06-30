package app.vaj.book.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaBookAuthorRepository extends JpaRepository<BookAuthorEntity, BookAuthorEntity.BookAuthorId> {
    List<BookAuthorEntity> findByBookId(UUID bookId);
    void deleteByBookId(UUID bookId);
}