package app.vaj.category.application.command;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateCategoryCommand(
        @Size(min = 2, max = 100) String name,
        @Size(max = 1000) String description,
        @Min(0) @Max(9999) Integer displayOrder
) {}