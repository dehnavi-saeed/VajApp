package app.vaj.tag.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaBookTagRepository extends JpaRepository<BookTagEntity, BookTagEntity.BookTagId> {
    List<BookTagEntity> findByBookId(UUID bookId);

    List<BookTagEntity> findByTagId(UUID tagId);

    boolean existsByBookIdAndTagId(UUID bookId, UUID tagId);

    void deleteByBookIdAndTagId(UUID bookId, UUID tagId);
}