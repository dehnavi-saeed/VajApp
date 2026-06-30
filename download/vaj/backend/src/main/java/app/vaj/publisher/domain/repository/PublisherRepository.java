package app.vaj.publisher.domain.repository;

import app.vaj.publisher.domain.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PublisherRepository {
    Publisher save(Publisher publisher);
    Optional<Publisher> findById(UUID id);
    Page<Publisher> findAll(Pageable pageable);
    Page<Publisher> findByNameContaining(String name, Pageable pageable);
    boolean existsByName(String name);
    void delete(Publisher publisher);
}