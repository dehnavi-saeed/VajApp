package app.vaj.tag.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaTagRepository extends JpaRepository<TagEntity, UUID> {
    Optional<TagEntity> findByIdAndIsDeletedFalse(UUID id);

    java.util.List<TagEntity> findByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(UUID userId);

    java.util.List<TagEntity> findByUserIdAndIsDeletedFalseOrderByNameAsc(UUID userId);

    boolean existsByUserIdAndNameAndIsDeletedFalse(UUID userId, String name);
}