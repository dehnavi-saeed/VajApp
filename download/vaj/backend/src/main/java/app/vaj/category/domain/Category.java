package app.vaj.category.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Category extends BaseAggregateRoot {

    private String name;
    private String description;
    private int displayOrder;
    private CategoryStatus status;

    private Category(UUID id) {
        super(id);
    }

    public static Category create(UUID id, String name, String description, int displayOrder, Clock clock) {
        validateName(name);
        if (displayOrder < 0) {
            throw new DomainValidationException("INVALID_ORDER", List.of("Display order must be non-negative."));
        }
        Category c = new Category(id);
        c.name = name;
        c.description = description;
        c.displayOrder = displayOrder;
        c.status = CategoryStatus.ACTIVE;
        c.markCreated(Instant.now(clock), null);
        return c;
    }

    public void update(String name, String description, Integer displayOrder, Clock clock) {
        if (name != null) validateName(name);
        if (name != null) this.name = name;
        if (description != null) {
            if (description.length() > 1000) {
                throw new DomainValidationException("LONG_DESC", List.of("Description max 1000 characters."));
            }
            this.description = description;
        }
        if (displayOrder != null && displayOrder >= 0) this.displayOrder = displayOrder;
        markUpdated(Instant.now(clock), null);
    }

    public void activate(Clock clock) {
        if (status == CategoryStatus.DELETED) {
            throw new DomainValidationException("CANNOT_ACTIVATE", List.of("Cannot activate a deleted category."));
        }
        this.status = CategoryStatus.ACTIVE;
        markUpdated(Instant.now(clock), null);
    }

    public void deactivate(Clock clock) {
        if (status == CategoryStatus.DELETED) {
            throw new DomainValidationException("CANNOT_DEACTIVATE", List.of("Cannot deactivate a deleted category."));
        }
        this.status = CategoryStatus.INACTIVE;
        markUpdated(Instant.now(clock), null);
    }

    public void delete(Clock clock) {
        this.status = CategoryStatus.DELETED;
        markDeleted(Instant.now(clock), null);
    }

    private static void validateName(String name) {
        List<String> errors = new ArrayList<>();
        if (name == null || name.isBlank()) {
            errors.add("Name is required.");
        } else if (name.length() < 2 || name.length() > 100) {
            errors.add("Name must be 2-100 characters.");
        }
        if (!errors.isEmpty()) {
            throw new DomainValidationException("INVALID_CATEGORY_NAME", errors);
        }
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getDisplayOrder() { return displayOrder; }
    public CategoryStatus getStatus() { return status; }
}