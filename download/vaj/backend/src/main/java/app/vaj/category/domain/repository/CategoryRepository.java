package app.vaj.category.domain.repository;

import app.vaj.category.domain.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(UUID id);
    List<Category> findAllActive();
    List<Category> findAll();
    boolean existsByName(String name);
}