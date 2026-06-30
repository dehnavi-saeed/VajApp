package app.vaj.book.domain.repository;

import app.vaj.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(UUID id);
    Page<Book> findByLibraryId(UUID libraryId, Pageable pageable);
    Page<Book> findByLibraryIdAndStatus(UUID libraryId, String status, Pageable pageable);
}