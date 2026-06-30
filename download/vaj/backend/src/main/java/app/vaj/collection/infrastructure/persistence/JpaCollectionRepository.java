package app.vaj.collection.infrastructure.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import java.util.UUID;
public interface JpaCollectionRepository extends JpaRepository<CollectionEntity, UUID> {
    Optional<CollectionEntity> findByIdAndIsDeletedFalse(UUID id);
    List<CollectionEntity> findByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(UUID userId);
}