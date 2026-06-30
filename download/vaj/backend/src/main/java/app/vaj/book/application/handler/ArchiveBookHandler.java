package app.vaj.book.application.handler;

import app.vaj.book.application.command.ArchiveBookCommand;
import app.vaj.book.application.dto.BookResponse;
import app.vaj.book.domain.Book;
import app.vaj.book.domain.repository.BookRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class ArchiveBookHandler {

    private final BookRepository bookRepository;
    private final Clock clock;

    public ArchiveBookHandler(BookRepository bookRepository, Clock clock) {
        this.bookRepository = bookRepository;
        this.clock = clock;
    }

    @Transactional
    public BookResponse handle(ArchiveBookCommand command) {
        Book book = bookRepository.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException("Book", command.id()));
        book.archive(clock);
        bookRepository.save(book);
        return toResponse(book);
    }

    private BookResponse toResponse(Book b) {
        return new BookResponse(b.getId(), b.getLibraryId(), b.getTitle(), b.getSubtitle(),
                b.getIsbn(), b.getDescription(), b.getLanguage(), b.getPageCount(),
                b.getFormat().name(), b.getCoverUrl(), b.getStatus().name(),
                java.util.List.of(), null, b.getCreatedAt(), b.getUpdatedAt());
    }
}