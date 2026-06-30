package app.vaj.category.application.handler;

import app.vaj.category.application.command.UpdateCategoryCommand;
import app.vaj.category.application.dto.CategoryResponse;
import app.vaj.category.domain.Category;
import app.vaj.category.domain.repository.CategoryRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class UpdateCategoryHandler {

    private final CategoryRepository repo;
    private final Clock clock;

    public UpdateCategoryHandler(CategoryRepository repo, Clock clock) {
        this.repo = repo;
        this.clock = clock;
    }

    @Transactional
    public CategoryResponse handle(UUID id, UpdateCategoryCommand cmd) {
        Category category = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category", id));
        category.update(cmd.name(), cmd.description(), cmd.displayOrder(), clock);
        repo.save(category);
        return new CategoryResponse(category.getId(), category.getName(), category.getDescription(),
                category.getDisplayOrder(), category.getStatus().name(), category.getCreatedAt(), category.getUpdatedAt());
    }
}