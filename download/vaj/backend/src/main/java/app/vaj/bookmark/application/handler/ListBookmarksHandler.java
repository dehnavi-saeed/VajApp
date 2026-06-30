package app.vaj.bookmark.application.handler;

import app.vaj.bookmark.application.dto.BookmarkResponse;
import app.vaj.bookmark.domain.Bookmark;
import app.vaj.bookmark.domain.repository.BookmarkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ListBookmarksHandler {
    private final BookmarkRepository repository;
    public ListBookmarksHandler(BookmarkRepository repository) { this.repository = repository; }

    @Transactional(readOnly = true)
    public List<BookmarkResponse> handle(UUID bookId) {
        return repository.findByBookId(bookId).stream()
                .map(b -> new BookmarkResponse(b.getId(), b.getBookId(), b.getPage(), b.getTitle(),
                        b.getDescription(), b.getColor(), b.getSortOrder(), b.getCreatedAt()))
                .toList();
    }
}