package app.vaj.author.domain.repository;

import app.vaj.author.domain.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository {
    Author save(Author author);
    Optional<Author> findById(UUID id);
    Page<Author> findAll(Pageable pageable);
    boolean existsByName(String name);
    Page<Author> findByNameContaining(String query, Pageable pageable);
    void delete(Author author);
}