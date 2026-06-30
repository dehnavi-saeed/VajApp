package app.vaj.author.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaAuthorRepository extends JpaRepository<AuthorEntity, UUID> {

    Optional<AuthorEntity> findByName(String name);

    Page<AuthorEntity> findByNameContainingIgnoreCaseAndIsDeletedFalse(String query, Pageable pageable);

    Page<AuthorEntity> findByIsDeletedFalse(Pageable pageable);

    Optional<AuthorEntity> findByIdAndIsDeletedFalse(UUID id);

    boolean existsByNameAndIsDeletedFalse(String name);
}