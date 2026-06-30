package app.vaj.book.application.handler;

import app.vaj.book.application.command.DeleteBookCommand;
import app.vaj.book.domain.Book;
import app.vaj.book.domain.repository.BookRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@Service
public class DeleteBookHandler {

    private final BookRepository bookRepository;
    private final Clock clock;

    public DeleteBookHandler(BookRepository bookRepository, Clock clock) {
        this.bookRepository = bookRepository;
        this.clock = clock;
    }

    @Transactional
    public void handle(DeleteBookCommand command) {
        Book book = bookRepository.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException("Book", command.id()));
        book.delete(clock);
        bookRepository.save(book);
    }
}