package app.vaj.common.domain;

import java.util.List;

public class DomainValidationException extends DomainException {

    private final List<String> violations;

    public DomainValidationException(String code, List<String> violations) {
        super(code, String.join(", ", violations));
        this.violations = List.copyOf(violations);
    }

    public List<String> getViolations() {
        return violations;
    }
}