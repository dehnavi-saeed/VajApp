package app.vaj.book.infrastructure.persistence;

import app.vaj.book.domain.*;
import app.vaj.book.domain.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final JpaBookRepository jpa;

    public BookRepositoryImpl(JpaBookRepository jpa) { this.jpa = jpa; }

    @Override
    public Book save(Book book) {
        jpa.save(toEntity(book));
        return book;
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return jpa.findByIdAndIsDeletedFalse(id).map(this::toDomain);
    }

    @Override
    public Page<Book> findByLibraryId(UUID libraryId, Pageable pageable) {
        return jpa.findByLibraryIdAndIsDeletedFalse(libraryId, pageable).map(this::toDomain);
    }

    @Override
    public Page<Book> findByLibraryIdAndStatus(UUID libraryId, String status, Pageable pageable) {
        return jpa.findByLibraryIdAndStatusAndIsDeletedFalse(libraryId, status, pageable).map(this::toDomain);
    }

    private Book toDomain(BookEntity e) {
        try {
            var ctor = Book.class.getDeclaredConstructor(UUID.class);
            ctor.setAccessible(true);
            Book b = ctor.newInstance(e.getId());
            setField(b, "libraryId", e.getLibraryId());
            setField(b, "title", e.getTitle());
            setField(b, "subtitle", e.getSubtitle());
            setField(b, "isbn", e.getIsbn());
            setField(b, "description", e.getDescription());
            setField(b, "language", e.getLanguage());
            setField(b, "pageCount", e.getPageCount());
            setField(b, "format", e.getFormat() != null ? BookFormat.valueOf(e.getFormat().name()) : BookFormat.PHYSICAL);
            setField(b, "coverUrl", e.getCoverUrl());
            setField(b, "status", e.getStatus() != null ? ReadingStatus.valueOf(e.getStatus().name()) : ReadingStatus.UNREAD);
            setField(b, "createdAt", e.getCreatedAt());
            setField(b, "updatedAt", e.getUpdatedAt());
            return b;
        } catch (Exception ex) {
            throw new RuntimeException("Mapping failed", ex);
        }
    }

    private BookEntity toEntity(Book book) {
        BookEntity e = new BookEntity();
        e.setId(book.getId());
        e.setLibraryId(book.getLibraryId());
        e.setTitle(book.getTitle());
        e.setSubtitle(book.getSubtitle());
        e.setIsbn(book.getIsbn());
        e.setDescription(book.getDescription());
        e.setLanguage(book.getLanguage());
        e.setPageCount(book.getPageCount());
        e.setFormat(BookEntity.FormatJpa.valueOf(book.getFormat().name()));
        e.setStatus(BookEntity.StatusJpa.valueOf(book.getStatus().name()));
        e.setCoverUrl(book.getCoverUrl());
        e.setCreatedAt(book.getCreatedAt() != null ? book.getCreatedAt() : Instant.now());
        e.setUpdatedAt(book.getUpdatedAt() != null ? book.getUpdatedAt() : Instant.now());
        e.setVersion(book.getVersion() != null ? book.getVersion() : 0L);
        e.setDeleted(book.isDeleted());
        return e;
    }

    private void setField(Object target, String name, Object value) {
        try {
            Field f = target.getClass().getDeclaredField(name);
            f.setAccessible(true);
            f.set(target, value);
        } catch (NoSuchFieldException ex) {
            try {
                Field f = target.getClass().getSuperclass().getDeclaredField(name);
                f.setAccessible(true);
                f.set(target, value);
            } catch (Exception ignored) {}
        } catch (IllegalAccessException ignored) {}
    }
}