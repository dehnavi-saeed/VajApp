package app.vaj.category.infrastructure.persistence;

import app.vaj.category.domain.Category;
import app.vaj.category.domain.CategoryStatus;
import app.vaj.category.domain.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JpaCategoryRepository jpa;

    public CategoryRepositoryImpl(JpaCategoryRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Category save(Category category) {
        jpa.save(toEntity(category));
        return category;
    }

    @Override
    public Optional<Category> findById(UUID id) {
        return jpa.findByIdAndIsDeletedFalse(id).map(this::toDomain);
    }

    @Override
    public List<Category> findAllActive() {
        return jpa.findByIsDeletedFalseOrderByDisplayOrderAscNameAsc().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Category> findAll() {
        return jpa.findByStatusNotAndIsDeletedFalseOrderByDisplayOrderAscNameAsc(CategoryEntity.StatusJpa.DELETED).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return jpa.existsByNameAndIsDeletedFalse(name);
    }

    private Category toDomain(CategoryEntity e) {
        try {
            var constructor = Category.class.getDeclaredConstructor(UUID.class);
            constructor.setAccessible(true);
            Category c = constructor.newInstance(e.getId());
            setField(c, "name", e.getName());
            setField(c, "description", e.getDescription());
            setField(c, "displayOrder", e.getDisplayOrder());
            setField(c, "status", CategoryStatus.valueOf(e.getStatus().name()));
            setField(c, "createdAt", e.getCreatedAt());
            setField(c, "updatedAt", e.getUpdatedAt());
            return c;
        } catch (Exception ex) {
            throw new RuntimeException("Category mapping failed", ex);
        }
    }

    private CategoryEntity toEntity(Category c) {
        CategoryEntity e = new CategoryEntity();
        e.setId(c.getId());
        e.setName(c.getName());
        e.setDescription(c.getDescription());
        e.setDisplayOrder(c.getDisplayOrder());
        e.setStatus(CategoryEntity.StatusJpa.valueOf(c.getStatus().name()));
        e.setCreatedAt(c.getCreatedAt() != null ? c.getCreatedAt() : java.time.Instant.now());
        e.setUpdatedAt(c.getUpdatedAt() != null ? c.getUpdatedAt() : java.time.Instant.now());
        e.setVersion(c.getVersion() != null ? c.getVersion() : 0L);
        e.setDeleted(c.isDeleted());
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
            } catch (Exception e) {
                // skip
            }
        }
    }
}