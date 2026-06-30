package app.vaj.library.domain.repository;

import app.vaj.library.domain.Library;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface LibraryRepository {
    Library save(Library library);
    Optional<Library> findById(UUID id);
    Page<Library> findByUserId(UUID userId, Pageable pageable);
    boolean existsByUserIdAndName(UUID userId, String name);
    long countByUserId(UUID userId);
}