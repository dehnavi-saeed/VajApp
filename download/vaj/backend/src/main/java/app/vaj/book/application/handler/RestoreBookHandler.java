package app.vaj.book.application.handler;

import app.vaj.book.application.command.RestoreBookCommand;
import app.vaj.book.application.dto.BookResponse;
import app.vaj.book.domain.Book;
import app.vaj.book.domain.repository.BookRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@Service
public class RestoreBookHandler {

    private final BookRepository bookRepository;
    private final Clock clock;

    public RestoreBookHandler(BookRepository bookRepository, Clock clock) {
        this.bookRepository = bookRepository;
        this.clock = clock;
    }

    @Transactional
    public BookResponse handle(RestoreBookCommand command) {
        Book book = bookRepository.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException("Book", command.id()));
        book.restore(clock);
        bookRepository.save(book);
        return new BookResponse(book.getId(), book.getLibraryId(), book.getTitle(), book.getSubtitle(),
                book.getIsbn(), book.getDescription(), book.getLanguage(), book.getPageCount(),
                book.getFormat().name(), book.getCoverUrl(), book.getStatus().name(),
                java.util.List.of(), null, book.getCreatedAt(), book.getUpdatedAt());
    }
}