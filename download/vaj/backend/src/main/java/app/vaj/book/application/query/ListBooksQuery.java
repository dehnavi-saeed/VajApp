package app.vaj.book.application.query;

import java.util.UUID;

public record ListBooksQuery(
        UUID libraryId,
        String status,
        int page,
        int size
) {
    public ListBooksQuery {
        if (page < 0) page = 0;
        if (size < 1) size = 20;
        if (size > 100) size = 100;
    }

    public ListBooksQuery(UUID libraryId) {
        this(libraryId, null, 0, 20);
    }

    public ListBooksQuery(UUID libraryId, String status) {
        this(libraryId, status, 0, 20);
    }
}