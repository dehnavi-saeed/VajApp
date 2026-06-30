package app.vaj.auth.application.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginCommand(
        @NotBlank String usernameOrEmail,
        @NotBlank String password
) {}