package app.vaj.author.application.command;

import app.vaj.author.domain.AuthorType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAuthorCommand(
        @NotBlank @Size(min = 2, max = 200) String name,
        @Size(max = 5000) String bio,
        AuthorType type
) {
    public CreateAuthorCommand {
        if (type == null) {
            type = AuthorType.PERSON;
        }
    }
}