package app.vaj.library.application.query;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

public record ListLibrariesQuery(
        @Min(0) int page,
        @Min(1) @Max(100) int size,
        String sort
) {
    public ListLibrariesQuery {
        if (page < 0) page = 0;
        if (size < 1) size = 20;
        if (size > 100) size = 100;
    }

    public ListLibrariesQuery() {
        this(0, 20, null);
    }
}