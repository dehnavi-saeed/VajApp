package app.vaj.bookmark.infrastructure.persistence;

import app.vaj.bookmark.domain.Bookmark;
import app.vaj.bookmark.domain.repository.BookmarkRepository;
import org.springframework.stereotype.Repository;
import java.lang.reflect.Field;
import java.util.*;

@Repository
public class BookmarkRepositoryImpl implements BookmarkRepository {
    private final JpaBookmarkRepository jpa;
    public BookmarkRepositoryImpl(JpaBookmarkRepository jpa) { this.jpa = jpa; }

    @Override public Bookmark save(Bookmark b) { jpa.save(toEntity(b)); return b; }
    @Override public Optional<Bookmark> findById(UUID id) { return jpa.findByIdAndIsDeletedFalse(id).map(this::toDomain); }
    @Override public List<Bookmark> findByBookId(UUID bookId) { return jpa.findByBookIdAndIsDeletedFalseOrderBySortOrderAsc(bookId).stream().map(this::toDomain).toList(); }
    @Override public void delete(Bookmark b) { jpa.save(toEntity(b)); }

    private Bookmark toDomain(BookmarkEntity e) {
        try {
            var c = Bookmark.class.getDeclaredConstructor(UUID.class); c.setAccessible(true);
            Bookmark b = c.newInstance(e.getId());
            set(b, "bookId", e.getBookId()); set(b, "userId", e.getUserId()); set(b, "page", e.getPage());
            set(b, "chapter", e.getChapter()); set(b, "title", e.getTitle()); set(b, "description", e.getDescription());
            set(b, "color", e.getColor()); set(b, "sortOrder", e.getSortOrder());
            set(b, "createdAt", e.getCreatedAt()); set(b, "updatedAt", e.getUpdatedAt());
            return b;
        } catch (Exception ex) { throw new RuntimeException("Mapping failed", ex); }
    }

    private BookmarkEntity toEntity(Bookmark b) {
        BookmarkEntity e = new BookmarkEntity();
        e.setId(b.getId()); e.setBookId(b.getBookId()); e.setUserId(b.getUserId()); e.setPage(b.getPage());
        e.setChapter(b.getChapter()); e.setTitle(b.getTitle()); e.setDescription(b.getDescription());
        e.setColor(b.getColor()); e.setSortOrder(b.getSortOrder());
        e.setCreatedAt(b.getCreatedAt() != null ? b.getCreatedAt() : java.time.Instant.now());
        e.setUpdatedAt(b.getUpdatedAt() != null ? b.getUpdatedAt() : java.time.Instant.now());
        e.setCreatedBy(b.getUserId()); e.setVersion(b.getVersion() != null ? b.getVersion() : 0L);
        e.setDeleted(b.isDeleted());
        return e;
    }

    private void set(Object t, String n, Object v) {
        try { Field f = t.getClass().getDeclaredField(n); f.setAccessible(true); f.set(t, v); }
        catch (Exception e) { try { Field f = t.getClass().getSuperclass().getDeclaredField(n); f.setAccessible(true); f.set(t, v); } catch (Exception ignored) {} }
    }
}