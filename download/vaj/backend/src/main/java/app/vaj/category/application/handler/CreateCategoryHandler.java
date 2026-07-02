package app.vaj.category.application.handler;

import app.vaj.category.application.command.CreateCategoryCommand;
import app.vaj.category.application.dto.CategoryResponse;
import app.vaj.category.domain.Category;
import app.vaj.category.domain.event.CategoryCreated;
import app.vaj.category.domain.repository.CategoryRepository;
import app.vaj.common.domain.DomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Service
public class CreateCategoryHandler {

    private final CategoryRepository repo;
    private final Clock clock;

    public CreateCategoryHandler(CategoryRepository repo, Clock clock) {
        this.repo = repo;
        this.clock = clock;
    }

    @Transactional
    public CategoryResponse handle(CreateCategoryCommand cmd) {
        if (repo.existsByName(cmd.name())) {
            throw new DomainException("CATEGORY_EXISTS", "Category with this name already exists.");
        }

        UUID id = UUID.randomUUID();
        int displayOrder = cmd.displayOrder() != null ? cmd.displayOrder() : 0;
        Category category = Category.create(id, cmd.name(), cmd.description(), displayOrder, clock);

        category.registerEvent(new CategoryCreated(UUID.randomUUID(), Instant.now(clock), id, cmd.name()));

        repo.save(category);

        return new CategoryResponse(category.getId(), category.getName(), category.getDescription(),
                category.getDisplayOrder(), category.getStatus().name(), category.getCreatedAt(), category.getUpdatedAt());
    }
}