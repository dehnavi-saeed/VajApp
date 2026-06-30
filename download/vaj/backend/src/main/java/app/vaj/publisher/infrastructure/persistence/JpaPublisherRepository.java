package app.vaj.publisher.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaPublisherRepository extends JpaRepository<PublisherEntity, UUID> {

    Optional<PublisherEntity> findByIdAndIsDeletedFalse(UUID id);

    Page<PublisherEntity> findByIsDeletedFalse(Pageable pageable);

    Page<PublisherEntity> findByNameContainingIgnoreCaseAndIsDeletedFalse(String name, Pageable pageable);

    boolean existsByNameAndIsDeletedFalse(String name);
}