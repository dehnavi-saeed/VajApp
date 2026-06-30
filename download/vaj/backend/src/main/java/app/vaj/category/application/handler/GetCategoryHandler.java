package app.vaj.category.application.handler;

import app.vaj.category.application.dto.CategoryResponse;
import app.vaj.category.domain.Category;
import app.vaj.category.domain.repository.CategoryRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.List;
import java.util.UUID;

@Service
public class GetCategoryHandler {

    private final CategoryRepository repo;

    public GetCategoryHandler(CategoryRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public CategoryResponse handle(UUID id) {
        Category category = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category", id));
        return toResponse(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> handleListAll() {
        return repo.findAllActive().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> handleListAllIncludingInactive() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private CategoryResponse toResponse(Category c) {
        return new CategoryResponse(c.getId(), c.getName(), c.getDescription(),
                c.getDisplayOrder(), c.getStatus().name(), c.getCreatedAt(), c.getUpdatedAt());
    }
}