package app.vaj.publisher.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Publisher extends BaseAggregateRoot {

    private String name;
    private String website;
    private String country;
    private PublisherStatus status;

    private Publisher(UUID id) {
        super(id);
    }

    public static Publisher create(UUID id, String name, String website, String country, Clock clock) {
        validateName(name);
        validateCountry(country);
        Publisher publisher = new Publisher(id);
        publisher.name = name;
        publisher.website = website;
        publisher.country = country;
        publisher.status = PublisherStatus.ACTIVE;
        publisher.markCreated(Instant.now(clock), null);
        return publisher;
    }

    public void update(String name, String website, String country, UUID userId, Clock clock) {
        if (name != null) {
            validateName(name);
            this.name = name;
        }
        if (website != null) {
            this.website = website;
        }
        if (country != null) {
            validateCountry(country);
            this.country = country;
        }
        markUpdated(Instant.now(clock), userId);
    }

    public void softDelete(UUID userId, Clock clock) {
        this.status = PublisherStatus.DELETED;
        markDeleted(Instant.now(clock), userId);
    }

    private static void validateName(String name) {
        List<String> errors = new ArrayList<>();
        if (name == null || name.isBlank()) {
            errors.add("Publisher name is required.");
        } else if (name.length() < 2 || name.length() > 200) {
            errors.add("Publisher name must be between 2 and 200 characters.");
        }
        if (!errors.isEmpty()) {
            throw new DomainValidationException("INVALID_PUBLISHER_NAME", errors);
        }
    }

    private static void validateCountry(String country) {
        if (country != null && country.length() > 3) {
            throw new DomainValidationException("INVALID_PUBLISHER_COUNTRY",
                    List.of("Country must be a valid ISO code (max 3 characters)."));
        }
    }

    public String getName() { return name; }
    public String getWebsite() { return website; }
    public String getCountry() { return country; }
    public PublisherStatus getStatus() { return status; }
}