package app.vaj.highlight.application.command;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
public record CreateHighlightCommand(
    @NotNull UUID bookId, Integer page, @Min(1) int startPosition, @Min(1) int endPosition,
    @NotBlank String textSnapshot, String color, String comment
) {}