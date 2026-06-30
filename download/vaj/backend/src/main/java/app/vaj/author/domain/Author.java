package app.vaj.author.domain;

import app.vaj.common.domain.BaseAggregateRoot;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

public class Author extends BaseAggregateRoot {

    private String name;
    private String bio;

    private Author(UUID id) {
        super(id);
    }

    public static Author create(UUID id, String name, String bio, Clock clock) {
        if (name == null || name.isBlank() || name.length() > 200) {
            throw new app.vaj.common.domain.DomainValidationException(
                "INVALID_AUTHOR_NAME", java.util.List.of("Author name is required (max 200 chars)."));
        }
        Author author = new Author(id);
        author.name = name;
        author.bio = bio;
        author.markCreated(Instant.now(clock), null);
        return author;
    }

    public void update(String name, String bio, UUID userId, Clock clock) {
        if (name != null) this.name = name;
        if (bio != null) this.bio = bio;
        markUpdated(Instant.now(clock), userId);
    }

    public String getName() { return name; }
    public String getBio() { return bio; }
}