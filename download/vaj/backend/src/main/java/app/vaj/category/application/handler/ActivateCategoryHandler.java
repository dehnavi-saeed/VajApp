package app.vaj.category.application.handler;

import app.vaj.category.domain.Category;
import app.vaj.category.domain.repository.CategoryRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class ActivateCategoryHandler {

    private final CategoryRepository repo;
    private final Clock clock;

    public ActivateCategoryHandler(CategoryRepository repo, Clock clock) {
        this.repo = repo;
        this.clock = clock;
    }

    @Transactional
    public void handle(UUID id) {
        Category category = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category", id));
        category.activate(clock);
        repo.save(category);
    }
}