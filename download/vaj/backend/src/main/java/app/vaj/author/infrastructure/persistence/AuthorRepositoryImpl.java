package app.vaj.author.infrastructure.persistence;

import app.vaj.author.domain.Author;
import app.vaj.author.domain.AuthorStatus;
import app.vaj.author.domain.AuthorType;
import app.vaj.author.domain.repository.AuthorRepository;
import app.vaj.author.infrastructure.mapper.AuthorMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    private final JpaAuthorRepository jpa;
    private final AuthorMapper authorMapper;

    public AuthorRepositoryImpl(JpaAuthorRepository jpa, AuthorMapper authorMapper) {
        this.jpa = jpa;
        this.authorMapper = authorMapper;
    }

    @Override
    public Author save(Author author) {
        AuthorEntity entity = authorMapper.toEntity(author);
        jpa.save(entity);
        return author;
    }

    @Override
    public Optional<Author> findById(UUID id) {
        return jpa.findByIdAndIsDeletedFalse(id).map(this::toDomain);
    }

    @Override
    public Page<Author> findAll(Pageable pageable) {
        return jpa.findByIsDeletedFalse(pageable).map(this::toDomain);
    }

    @Override
    public boolean existsByName(String name) {
        return jpa.existsByNameAndIsDeletedFalse(name);
    }

    @Override
    public Page<Author> findByNameContaining(String query, Pageable pageable) {
        return jpa.findByNameContainingIgnoreCaseAndIsDeletedFalse(query, pageable)
                .map(this::toDomain);
    }

    @Override
    public void delete(Author author) {
        AuthorEntity entity = authorMapper.toEntity(author);
        jpa.save(entity);
    }

    private Author toDomain(AuthorEntity e) {
        try {
            java.lang.reflect.Constructor<Author> ctor = Author.class.getDeclaredConstructor(UUID.class);
            ctor.setAccessible(true);
            Author author = ctor.newInstance(e.getId());
            setField(author, "name", e.getName());
            setField(author, "bio", e.getBio());
            setField(author, "type", e.getType() != null ? AuthorType.valueOf(e.getType()) : AuthorType.PERSON);
            setField(author, "status", e.getStatus() != null ? AuthorStatus.valueOf(e.getStatus()) : AuthorStatus.ACTIVE);
            setField(author, "createdAt", e.getCreatedAt());
            setField(author, "updatedAt", e.getUpdatedAt());
            setField(author, "version", e.getVersion());
            if (e.getDeletedAt() != null) {
                setField(author, "deletedAt", e.getDeletedAt());
            }
            return author;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to reconstruct Author from entity", ex);
        }
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = findField(target.getClass(), fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private static Field findField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Class<?> current = clazz;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }
}