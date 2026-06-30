package app.vaj.bookmark.application.handler;

import app.vaj.bookmark.application.command.CreateBookmarkCommand;
import app.vaj.bookmark.application.dto.BookmarkResponse;
import app.vaj.bookmark.domain.Bookmark;
import app.vaj.bookmark.domain.repository.BookmarkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;

@Service
public class CreateBookmarkHandler {
    private final BookmarkRepository repository;
    private final Clock clock;
    public CreateBookmarkHandler(BookmarkRepository repository, Clock clock) { this.repository = repository; this.clock = clock; }

    @Transactional
    public BookmarkResponse handle(UUID userId, CreateBookmarkCommand cmd) {
        Bookmark b = Bookmark.create(UUID.randomUUID(), cmd.bookId(), userId, cmd.page(),
                cmd.title(), cmd.description(), cmd.color(), 0, clock);
        repository.save(b);
        return new BookmarkResponse(b.getId(), b.getBookId(), b.getPage(), b.getTitle(),
                b.getDescription(), b.getColor(), b.getSortOrder(), b.getCreatedAt());
    }
}