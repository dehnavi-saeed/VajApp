package app.vaj.book.application.handler;

import app.vaj.book.application.dto.BookResponse;
import app.vaj.book.application.query.ListBooksQuery;
import app.vaj.book.domain.Book;
import app.vaj.book.domain.repository.BookRepository;
import app.vaj.book.infrastructure.mapper.BookMapper;
import app.vaj.common.application.dto.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListBooksHandler {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public ListBooksHandler(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<BookResponse> handle(ListBooksQuery query) {
        PageRequest pageable = PageRequest.of(query.page(), query.size());
        Page<Book> result = query.status() != null
                ? bookRepository.findByLibraryIdAndStatus(query.libraryId(), query.status(), pageable)
                : bookRepository.findByLibraryId(query.libraryId(), pageable);

        List<BookResponse> items = result.getContent().stream()
                .map(bookMapper::toResponse)
                .toList();

        return PaginatedResponse.of(items, query.page(), query.size(), result.getTotalElements());
    }
}