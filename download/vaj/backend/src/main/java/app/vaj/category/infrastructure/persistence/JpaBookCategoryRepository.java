package app.vaj.category.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaBookCategoryRepository extends JpaRepository<BookCategoryEntity, BookCategoryEntity.BookCategoryId> {

    List<BookCategoryEntity> findByBookId(UUID bookId);

    List<BookCategoryEntity> findByCategoryId(UUID categoryId);

    boolean existsByBookIdAndCategoryId(UUID bookId, UUID categoryId);

    void deleteByBookIdAndCategoryId(UUID bookId, UUID categoryId);
}