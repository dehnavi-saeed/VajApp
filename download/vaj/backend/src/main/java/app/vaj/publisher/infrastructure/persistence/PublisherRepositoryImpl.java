package app.vaj.publisher.infrastructure.persistence;

import app.vaj.publisher.domain.Publisher;
import app.vaj.publisher.domain.PublisherStatus;
import app.vaj.publisher.domain.repository.PublisherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PublisherRepositoryImpl implements PublisherRepository {

    private final JpaPublisherRepository jpa;

    public PublisherRepositoryImpl(JpaPublisherRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Publisher save(Publisher publisher) {
        jpa.save(toEntity(publisher));
        return publisher;
    }

    @Override
    public Optional<Publisher> findById(UUID id) {
        return jpa.findByIdAndIsDeletedFalse(id).map(this::toDomain);
    }

    @Override
    public Page<Publisher> findAll(Pageable pageable) {
        return jpa.findByIsDeletedFalse(pageable).map(this::toDomain);
    }

    @Override
    public Page<Publisher> findByNameContaining(String name, Pageable pageable) {
        return jpa.findByNameContainingIgnoreCaseAndIsDeletedFalse(name, pageable).map(this::toDomain);
    }

    @Override
    public boolean existsByName(String name) {
        return jpa.existsByNameAndIsDeletedFalse(name);
    }

    @Override
    public void delete(Publisher publisher) {
        jpa.save(toEntity(publisher));
    }

    private Publisher toDomain(PublisherEntity e) {
        try {
            var constructor = Publisher.class.getDeclaredConstructor(UUID.class);
            constructor.setAccessible(true);
            Publisher p = constructor.newInstance(e.getId());
            setField(p, "name", e.getName());
            setField(p, "website", e.getWebsite());
            setField(p, "country", e.getCountry());
            setField(p, "status", PublisherStatus.valueOf(e.getStatus().name()));
            setField(p, "createdAt", e.getCreatedAt());
            setField(p, "updatedAt", e.getUpdatedAt());
            return p;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to map PublisherEntity to domain", ex);
        }
    }

    private PublisherEntity toEntity(Publisher p) {
        PublisherEntity e = new PublisherEntity();
        e.setId(p.getId());
        e.setName(p.getName());
        e.setWebsite(p.getWebsite());
        e.setCountry(p.getCountry());
        e.setStatus(PublisherEntity.StatusJpa.valueOf(p.getStatus().name()));
        e.setCreatedAt(p.getCreatedAt() != null ? p.getCreatedAt() : java.time.Instant.now());
        e.setUpdatedAt(p.getUpdatedAt() != null ? p.getUpdatedAt() : java.time.Instant.now());
        e.setVersion(p.getVersion() != null ? p.getVersion() : 0L);
        e.setDeleted(p.isDeleted());
        return e;
    }

    private void setField(Object target, String name, Object value) {
        try {
            Field f = target.getClass().getDeclaredField(name);
            f.setAccessible(true);
            f.set(target, value);
        } catch (Exception ignored) {
            try {
                Field f = target.getClass().getSuperclass().getDeclaredField(name);
                f.setAccessible(true);
                f.set(target, value);
            } catch (Exception ignored2) {}
        }
    }
}