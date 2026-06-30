package app.vaj.auth.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordCommand(
        @NotBlank String token,
        @NotBlank @Size(min = 8, max = 100) String newPassword
) {}