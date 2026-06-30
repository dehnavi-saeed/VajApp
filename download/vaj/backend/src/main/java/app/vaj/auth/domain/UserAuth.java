package app.vaj.auth.domain;

import app.vaj.common.domain.BaseAggregateRoot;
import app.vaj.common.domain.DomainValidationException;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserAuth extends BaseAggregateRoot {

    private String username;
    private String email;
    private String passwordHash;
    private UserAuthStatus status;

    private UserAuth(UUID id) {
        super(id);
    }

    public static UserAuth create(UUID id, String username, String email, String passwordHash, Clock clock) {
        validateRegistration(username, email, passwordHash);
        UserAuth user = new UserAuth(id);
        user.username = username;
        user.email = email;
        user.passwordHash = passwordHash;
        user.status = UserAuthStatus.PENDING;
        user.markCreated(Instant.now(clock), id);
        return user;
    }

    public void activate(Clock clock) {
        if (status == UserAuthStatus.DELETED) {
            throw new DomainValidationException("INVALID_STATE", List.of("Cannot activate a deleted user."));
        }
        this.status = UserAuthStatus.ACTIVE;
        markUpdated(Instant.now(clock), getId());
    }

    public void suspend(Clock clock) {
        if (status != UserAuthStatus.ACTIVE) {
            throw new DomainValidationException("INVALID_STATE", List.of("Only active users can be suspended."));
        }
        this.status = UserAuthStatus.SUSPENDED;
        markUpdated(Instant.now(clock), getId());
    }

    public void delete(Clock clock) {
        this.status = UserAuthStatus.DELETED;
        markDeleted(Instant.now(clock), getId());
    }

    public void changePassword(String newPasswordHash, Clock clock) {
        if (newPasswordHash == null || newPasswordHash.isBlank()) {
            throw new DomainValidationException("INVALID_PASSWORD", List.of("New password hash is required."));
        }
        this.passwordHash = newPasswordHash;
        markUpdated(Instant.now(clock), getId());
    }

    public boolean canLogin() {
        return status == UserAuthStatus.ACTIVE && !isDeleted();
    }

    private static void validateRegistration(String username, String email, String passwordHash) {
        List<String> errors = new ArrayList<>();
        if (username == null || username.isBlank()) {
            errors.add("Username is required.");
        } else if (username.length() < 3 || username.length() > 100) {
            errors.add("Username must be between 3 and 100 characters.");
        }
        if (email == null || email.isBlank()) {
            errors.add("Email is required.");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            errors.add("Invalid email format.");
        }
        if (passwordHash == null || passwordHash.isBlank()) {
            errors.add("Password hash is required.");
        }
        if (!errors.isEmpty()) {
            throw new DomainValidationException("REGISTRATION_VALIDATION_FAILED", errors);
        }
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public UserAuthStatus getStatus() { return status; }
}