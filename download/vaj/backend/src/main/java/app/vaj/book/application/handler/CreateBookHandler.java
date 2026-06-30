package app.vaj.book.application.handler;

import app.vaj.book.application.command.CreateBookCommand;
import app.vaj.book.application.dto.BookResponse;
import app.vaj.book.domain.Book;
import app.vaj.book.domain.BookFormat;
import app.vaj.book.domain.repository.BookRepository;
import app.vaj.common.domain.EntityNotFoundException;
import app.vaj.library.domain.Library;
import app.vaj.library.domain.repository.LibraryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class CreateBookHandler {

    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;
    private final Clock clock;

    public CreateBookHandler(BookRepository bookRepository, LibraryRepository libraryRepository, Clock clock) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
        this.clock = clock;
    }

    @Transactional
    public BookResponse handle(UUID userId, CreateBookCommand command) {
        Library library = libraryRepository.findById(command.libraryId())
                .orElseThrow(() -> new EntityNotFoundException("Library", command.libraryId()));

        BookFormat format = command.format() != null ?
                BookFormat.valueOf(command.format()) : BookFormat.PHYSICAL;

        Book book = Book.create(UUID.randomUUID(), library.getId(), command.title(),
                command.isbn(), command.language(), command.pageCount(), format, clock);

        if (command.subtitle() != null || command.description() != null) {
            book.updateMetadata(command.title(), command.subtitle(), command.description(),
                    command.language(), command.pageCount(), userId, clock);
        }

        bookRepository.save(book);

        return new BookResponse(book.getId(), book.getLibraryId(), book.getTitle(),
                book.getSubtitle(), book.getIsbn(), book.getDescription(), book.getLanguage(),
                book.getPageCount(), book.getFormat().name(), book.getCoverUrl(),
                book.getStatus().name(), command.authorIds(), command.publisherId(),
                book.getCreatedAt(), book.getUpdatedAt());
    }
}