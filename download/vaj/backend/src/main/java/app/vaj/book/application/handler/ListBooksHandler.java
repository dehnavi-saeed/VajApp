package app.vaj.book.application.handler;

import app.vaj.book.application.dto.BookResponse;
import app.vaj.book.application.query.ListBooksQuery;
import app.vaj.book.domain.Book;
import app.vaj.book.domain.repository.BookRepository;
import app.vaj.common.application.dto.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListBooksHandler {

    private final BookRepository bookRepository;

    public ListBooksHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<BookResponse> handle(ListBooksQuery query) {
        PageRequest pageable = PageRequest.of(query.page(), query.size());
        Page<Book> result = query.status() != null
                ? bookRepository.findByLibraryIdAndStatus(query.libraryId(), query.status(), pageable)
                : bookRepository.findByLibraryId(query.libraryId(), pageable);

        List<BookResponse> items = result.getContent().stream()
                .map(b -> new BookResponse(b.getId(), b.getLibraryId(), b.getTitle(), b.getSubtitle(),
                        b.getIsbn(), b.getDescription(), b.getLanguage(), b.getPageCount(),
                        b.getFormat().name(), b.getCoverUrl(), b.getStatus().name(),
                        java.util.List.of(), null, b.getCreatedAt(), b.getUpdatedAt()))
                .toList();

        return PaginatedResponse.of(items, query.page(), query.size(), result.getTotalElements());
    }
}