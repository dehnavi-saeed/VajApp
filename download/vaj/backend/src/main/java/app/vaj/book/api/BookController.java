package app.vaj.book.api;

import app.vaj.book.application.command.CreateBookCommand;
import app.vaj.book.application.dto.BookResponse;
import app.vaj.book.application.handler.CreateBookHandler;
import app.vaj.book.domain.Book;
import app.vaj.book.domain.repository.BookRepository;
import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.application.dto.PaginatedResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Books", description = "Book management")
public class BookController {

    private final BookRepository bookRepository;
    private final CreateBookHandler createBookHandler;

    public BookController(BookRepository bookRepository, CreateBookHandler createBookHandler) {
        this.bookRepository = bookRepository;
        this.createBookHandler = createBookHandler;
    }

    @GetMapping
    @Operation(summary = "List books in a library")
    public ResponseEntity<ApiResponse<PaginatedResponse<BookResponse>>> list(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestParam UUID libraryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String status) {
        Page<Book> result = status != null
                ? bookRepository.findByLibraryIdAndStatus(libraryId, status, PageRequest.of(page, size))
                : bookRepository.findByLibraryId(libraryId, PageRequest.of(page, size));
        var items = result.getContent().stream()
                .map(b -> new BookResponse(b.getId(), b.getLibraryId(), b.getTitle(), b.getSubtitle(),
                        b.getIsbn(), b.getDescription(), b.getLanguage(), b.getPageCount(),
                        b.getFormat().name(), b.getCoverUrl(), b.getStatus().name(),
                        java.util.List.of(), null, b.getCreatedAt(), b.getUpdatedAt()))
                .toList();
        return ResponseEntity.ok(ApiResponse.success(PaginatedResponse.of(items, page, size, result.getTotalElements())));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id")
    public ResponseEntity<ApiResponse<BookResponse>> getById(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        return bookRepository.findById(id)
                .map(b -> ResponseEntity.ok(ApiResponse.success(
                        new BookResponse(b.getId(), b.getLibraryId(), b.getTitle(), b.getSubtitle(),
                                b.getIsbn(), b.getDescription(), b.getLanguage(), b.getPageCount(),
                                b.getFormat().name(), b.getCoverUrl(), b.getStatus().name(),
                                java.util.List.of(), null, b.getCreatedAt(), b.getUpdatedAt()))))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new book")
    public ResponseEntity<ApiResponse<BookResponse>> create(
            @AuthenticationPrincipal CurrentUser currentUser,
            @Valid @RequestBody CreateBookCommand command) {
        BookResponse response = createBookHandler.handle(currentUser.id(), command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update book metadata")
    public ResponseEntity<ApiResponse<BookResponse>> update(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id,
            @RequestBody CreateBookCommand command) {
        return bookRepository.findById(id).map(book -> {
            book.updateMetadata(command.title(), command.subtitle(), command.description(),
                    command.language(), command.pageCount(), currentUser.id(), java.time.Clock.systemUTC());
            bookRepository.save(book);
            return ResponseEntity.ok(ApiResponse.success(
                    new BookResponse(book.getId(), book.getLibraryId(), book.getTitle(), book.getSubtitle(),
                            book.getIsbn(), book.getDescription(), book.getLanguage(), book.getPageCount(),
                            book.getFormat().name(), book.getCoverUrl(), book.getStatus().name(),
                            command.authorIds(), command.publisherId(), book.getCreatedAt(), book.getUpdatedAt())));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        return bookRepository.findById(id).map(book -> {
            book.delete(java.time.Clock.systemUTC());
            bookRepository.save(book);
            return ResponseEntity.ok(ApiResponse.<Void>success(null));
        }).orElse(ResponseEntity.notFound().build());
    }
}