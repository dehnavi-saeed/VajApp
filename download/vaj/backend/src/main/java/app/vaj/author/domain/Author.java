package app.vaj.author.domain;

import app.vaj.common.domain.BaseAggregateRoot;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Author extends BaseAggregateRoot {

    private String name;
    private String bio;
    private AuthorType type;
    private AuthorStatus status;

    private Author(UUID id) {
        super(id);
    }

    public static Author create(UUID id, String name, String bio, AuthorType type, Clock clock) {
        validateName(name);
        Author author = new Author(id);
        author.name = name;
        author.bio = bio;
        author.type = type != null ? type : AuthorType.PERSON;
        author.status = AuthorStatus.ACTIVE;
        author.markCreated(Instant.now(clock), null);
        return author;
    }

    public void update(String name, String bio, UUID userId, Clock clock) {
        if (name != null) {
            validateName(name);
            this.name = name;
        }
        if (bio != null) this.bio = bio;
        markUpdated(Instant.now(clock), userId);
    }

    public void softDelete(Clock clock) {
        this.status = AuthorStatus.DELETED;
        markDeleted(Instant.now(clock), null);
    }

    private static void validateName(String name) {
        List<String> errors = new ArrayList<>();
        if (name == null || name.isBlank()) {
            errors.add("Author name is required.");
        } else if (name.length() < 2 || name.length() > 200) {
            errors.add("Author name must be between 2 and 200 characters.");
        }
        if (!errors.isEmpty()) {
            throw new app.vaj.common.domain.DomainValidationException("INVALID_AUTHOR_NAME", errors);
        }
    }

    public String getName() { return name; }
    public String getBio() { return bio; }
    public AuthorType getType() { return type; }
    public AuthorStatus getStatus() { return status; }
}