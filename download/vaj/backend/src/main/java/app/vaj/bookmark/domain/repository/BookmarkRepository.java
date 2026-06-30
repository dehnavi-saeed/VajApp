package app.vaj.bookmark.domain.repository;

import app.vaj.bookmark.domain.Bookmark;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookmarkRepository {
    Bookmark save(Bookmark bookmark);
    Optional<Bookmark> findById(UUID id);
    List<Bookmark> findByBookId(UUID bookId);
    void delete(Bookmark bookmark);
}