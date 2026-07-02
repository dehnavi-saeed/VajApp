package app.vaj.category.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    Optional<CategoryEntity> findByIdAndIsDeletedFalse(UUID id);

    Optional<CategoryEntity> findByNameAndIsDeletedFalse(String name);

    List<CategoryEntity> findByStatusNotAndIsDeletedFalseOrderByDisplayOrderAscNameAsc(CategoryEntity.StatusJpa status);

    List<CategoryEntity> findByIsDeletedFalseOrderByDisplayOrderAscNameAsc();

    boolean existsByNameAndIsDeletedFalse(String name);
}