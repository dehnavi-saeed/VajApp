package app.vaj.book.application.handler;

import app.vaj.book.application.command.UpdateBookCommand;
import app.vaj.book.application.dto.BookResponse;
import app.vaj.book.domain.Book;
import app.vaj.book.domain.repository.BookRepository;
import app.vaj.book.infrastructure.mapper.BookMapper;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class UpdateBookHandler {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final Clock clock;

    public UpdateBookHandler(BookRepository bookRepository, BookMapper bookMapper, Clock clock) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.clock = clock;
    }

    @Transactional
    public BookResponse handle(UUID userId, UpdateBookCommand command) {
        Book book = bookRepository.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException("Book", command.id()));

        book.updateMetadata(command.title(), command.subtitle(), command.description(),
                command.language(), command.pageCount(), userId, clock);

        bookRepository.save(book);

        return bookMapper.toResponse(book);
    }

    @Transactional
    public BookResponse handleStartReading(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book", id));
        book.startReading(clock);
        bookRepository.save(book);
        return bookMapper.toResponse(book);
    }

    @Transactional
    public BookResponse handleCompleteReading(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book", id));
        book.completeReading(clock);
        bookRepository.save(book);
        return bookMapper.toResponse(book);
    }
}