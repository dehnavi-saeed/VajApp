package app.vaj.library.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaLibraryRepository extends JpaRepository<LibraryEntity, UUID> {

    Optional<LibraryEntity> findByIdAndIsDeletedFalse(UUID id);

    Page<LibraryEntity> findByUserIdAndIsDeletedFalse(UUID userId, Pageable pageable);

    List<LibraryEntity> findByUserIdAndIsDeletedFalse(UUID userId);

    boolean existsByUserIdAndNameAndIsDeletedFalse(UUID userId, String name);

    long countByUserIdAndIsDeletedFalse(UUID userId);
}