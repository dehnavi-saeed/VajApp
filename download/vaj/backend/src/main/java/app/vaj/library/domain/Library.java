package app.vaj.library.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Library extends BaseAggregateRoot {

    private UUID userId;
    private String name;
    private String description;
    private LibraryStatus status;

    private Library(UUID id) {
        super(id);
    }

    public static Library create(UUID id, UUID userId, String name, String description, Clock clock) {
        validateName(name);
        Library library = new Library(id);
        library.userId = userId;
        library.name = name;
        library.description = description;
        library.status = LibraryStatus.ACTIVE;
        library.markCreated(Instant.now(clock), userId);
        return library;
    }

    public void rename(String newName, Clock clock) {
        validateName(newName);
        this.name = newName;
        markUpdated(Instant.now(clock), userId);
    }

    public void updateDescription(String description, Clock clock) {
        if (description != null && description.length() > 1000) {
            throw new DomainValidationException("INVALID_DESCRIPTION",
                    List.of("Description must be at most 1000 characters."));
        }
        this.description = description;
        markUpdated(Instant.now(clock), userId);
    }

    public void archive(Clock clock) {
        if (status == LibraryStatus.DELETED) {
            throw new DomainValidationException("INVALID_STATE",
                    List.of("Cannot archive a deleted library."));
        }
        this.status = LibraryStatus.ARCHIVED;
        markUpdated(Instant.now(clock), userId);
    }

    public void restore(Clock clock) {
        if (status != LibraryStatus.ARCHIVED && status != LibraryStatus.ACTIVE) {
            throw new DomainValidationException("INVALID_STATE",
                    List.of("Only archived libraries can be restored."));
        }
        this.status = LibraryStatus.ACTIVE;
        markUpdated(Instant.now(clock), userId);
    }

    public void delete(Clock clock) {
        this.status = LibraryStatus.DELETED;
        markDeleted(Instant.now(clock), userId);
    }

    public boolean isArchived() {
        return status == LibraryStatus.ARCHIVED;
    }

    private static void validateName(String name) {
        List<String> errors = new ArrayList<>();
        if (name == null || name.isBlank()) {
            errors.add("Library name is required.");
        } else if (name.length() < 3 || name.length() > 100) {
            errors.add("Library name must be between 3 and 100 characters.");
        }
        if (!errors.isEmpty()) {
            throw new DomainValidationException("INVALID_LIBRARY_NAME", errors);
        }
    }

    public UUID getUserId() { return userId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LibraryStatus getStatus() { return status; }
}