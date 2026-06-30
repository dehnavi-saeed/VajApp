package app.vaj.tag.infrastructure.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import java.util.UUID;
public interface JpaTagRepository extends JpaRepository<TagEntity, UUID> {
    List<TagEntity> findByUserIdAndIsDeletedFalseOrderByNameAsc(UUID userId);
    boolean existsByUserIdAndNameAndIsDeletedFalse(UUID userId, String name);
}