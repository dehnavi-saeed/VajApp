package app.vaj.common.domain;

import java.util.ArrayList;
import java.util.List;

public class DomainValidator {

    private final List<String> violations = new ArrayList<>();

    public DomainValidator requireNonNull(Object value, String fieldName) {
        if (value == null) {
            violations.add(fieldName + " is required.");
        }
        return this;
    }

    public DomainValidator requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            violations.add(fieldName + " is required.");
        }
        return this;
    }

    public DomainValidator requireLength(String value, String fieldName, int min, int max) {
        if (value != null && (value.length() < min || value.length() > max)) {
            violations.add(fieldName + " must be between " + min + " and " + max + " characters.");
        }
        return this;
    }

    public DomainValidator requirePositive(Long value, String fieldName) {
        if (value != null && value <= 0) {
            violations.add(fieldName + " must be positive.");
        }
        return this;
    }

    public DomainValidator requireRange(Integer value, String fieldName, int min, int max) {
        if (value != null && (value < min || value > max)) {
            violations.add(fieldName + " must be between " + min + " and " + max + ".");
        }
        return this;
    }

    public void validate() {
        if (!violations.isEmpty()) {
            throw new DomainValidationException("VALIDATION_FAILED", violations);
        }
    }

    public List<String> getViolations() {
        return List.copyOf(violations);
    }
}