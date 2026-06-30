package app.vaj.book.application.handler;

import app.vaj.book.application.dto.BookResponse;
import app.vaj.book.domain.Book;
import app.vaj.book.domain.repository.BookRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GetBookHandler {

    private final BookRepository bookRepository;

    public GetBookHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public BookResponse handle(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book", id));
        return new BookResponse(book.getId(), book.getLibraryId(), book.getTitle(), book.getSubtitle(),
                book.getIsbn(), book.getDescription(), book.getLanguage(), book.getPageCount(),
                book.getFormat().name(), book.getCoverUrl(), book.getStatus().name(),
                java.util.List.of(), null, book.getCreatedAt(), book.getUpdatedAt());
    }
}