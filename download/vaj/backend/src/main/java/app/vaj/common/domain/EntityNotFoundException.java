package app.vaj.common.domain;

import java.util.UUID;

public class EntityNotFoundException extends DomainException {

    public EntityNotFoundException(String entityName, UUID id) {
        super(entityName.toUpperCase() + "_NOT_FOUND",
              entityName + " with id " + id + " was not found.");
    }

    public EntityNotFoundException(String code, String message) {
        super(code, message);
    }
}