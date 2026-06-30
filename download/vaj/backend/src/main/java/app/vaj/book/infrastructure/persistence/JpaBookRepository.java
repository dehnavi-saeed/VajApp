package app.vaj.book.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaBookRepository extends JpaRepository<BookEntity, UUID> {

    Optional<BookEntity> findByIdAndIsDeletedFalse(UUID id);

    Page<BookEntity> findByLibraryIdAndIsDeletedFalse(UUID libraryId, Pageable pageable);

    Page<BookEntity> findByLibraryIdAndStatusAndIsDeletedFalse(UUID libraryId, String status, Pageable pageable);
}