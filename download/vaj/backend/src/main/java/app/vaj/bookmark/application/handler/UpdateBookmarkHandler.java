package app.vaj.bookmark.application.handler;

import app.vaj.bookmark.application.command.UpdateBookmarkCommand;
import app.vaj.bookmark.application.dto.BookmarkResponse;
import app.vaj.bookmark.domain.Bookmark;
import app.vaj.bookmark.domain.repository.BookmarkRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;

@Service
public class UpdateBookmarkHandler {
    private final BookmarkRepository repository;
    private final Clock clock;
    public UpdateBookmarkHandler(BookmarkRepository repository, Clock clock) { this.repository = repository; this.clock = clock; }

    @Transactional
    public BookmarkResponse handle(UUID id, UpdateBookmarkCommand cmd) {
        Bookmark b = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bookmark", id));
        b.update(cmd.title(), cmd.description(), cmd.color(), cmd.page(), clock);
        repository.save(b);
        return new BookmarkResponse(b.getId(), b.getBookId(), b.getPage(), b.getTitle(),
                b.getDescription(), b.getColor(), b.getSortOrder(), b.getCreatedAt());
    }
}