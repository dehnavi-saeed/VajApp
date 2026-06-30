package app.vaj.bookmark.application.handler;

import app.vaj.bookmark.domain.Bookmark;
import app.vaj.bookmark.domain.repository.BookmarkRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Clock;
import java.util.UUID;

@Service
public class DeleteBookmarkHandler {
    private final BookmarkRepository repository;
    private final Clock clock;
    public DeleteBookmarkHandler(BookmarkRepository repository, Clock clock) { this.repository = repository; this.clock = clock; }

    @Transactional
    public void handle(UUID id) {
        Bookmark b = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bookmark", id));
        b.delete(clock);
        repository.delete(b);
    }
}