package app.vaj.bookmark.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import java.util.UUID;

public interface JpaBookmarkRepository extends JpaRepository<BookmarkEntity, UUID> {
    Optional<BookmarkEntity> findByIdAndIsDeletedFalse(UUID id);
    List<BookmarkEntity> findByBookIdAndIsDeletedFalseOrderBySortOrderAsc(UUID bookId);
}