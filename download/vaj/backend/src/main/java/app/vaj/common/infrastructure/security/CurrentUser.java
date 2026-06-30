package app.vaj.common.infrastructure.security;

import java.util.UUID;

public record CurrentUser(UUID id, String email, String username) {
}