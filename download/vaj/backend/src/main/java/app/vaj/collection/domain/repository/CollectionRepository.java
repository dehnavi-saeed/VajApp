package app.vaj.collection.domain.repository;
import app.vaj.collection.domain.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface CollectionRepository {
    Collection save(Collection c);
    Optional<Collection> findById(UUID id);
    List<Collection> findByUserId(UUID userId);
}