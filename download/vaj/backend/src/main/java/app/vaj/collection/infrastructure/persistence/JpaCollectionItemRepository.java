package app.vaj.collection.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaCollectionItemRepository extends JpaRepository<CollectionItemEntity, UUID> {
    List<CollectionItemEntity> findByCollectionId(UUID collectionId);

    boolean existsByCollectionIdAndItemTypeAndItemId(UUID collectionId, String itemType, UUID itemId);

    void deleteByCollectionIdAndItemTypeAndItemId(UUID collectionId, String itemType, UUID itemId);
}