package app.vaj.book.api;

import app.vaj.book.application.command.*;
import app.vaj.book.application.dto.BookResponse;
import app.vaj.book.application.handler.*;
import app.vaj.book.application.query.ListBooksQuery;
import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.application.dto.PaginatedResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Books", description = "Book management")
public class BookController {

    private final CreateBookHandler createBookHandler;
    private final GetBookHandler getBookHandler;
    private final ListBooksHandler listBooksHandler;
    private final ArchiveBookHandler archiveBookHandler;
    private final RestoreBookHandler restoreBookHandler;
    private final DeleteBookHandler deleteBookHandler;

    public BookController(CreateBookHandler createBookHandler,
                          GetBookHandler getBookHandler,
                          ListBooksHandler listBooksHandler,
                          ArchiveBookHandler archiveBookHandler,
                          RestoreBookHandler restoreBookHandler,
                          DeleteBookHandler deleteBookHandler) {
        this.createBookHandler = createBookHandler;
        this.getBookHandler = getBookHandler;
        this.listBooksHandler = listBooksHandler;
        this.archiveBookHandler = archiveBookHandler;
        this.restoreBookHandler = restoreBookHandler;
        this.deleteBookHandler = deleteBookHandler;
    }

    @GetMapping
    @Operation(summary = "List books in a library")
    public ResponseEntity<ApiResponse<PaginatedResponse<BookResponse>>> list(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestParam UUID libraryId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PaginatedResponse<BookResponse> result = listBooksHandler.handle(
                new ListBooksQuery(libraryId, status, page, size));
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id")
    public ResponseEntity<ApiResponse<BookResponse>> getById(@PathVariable UUID id) {
        BookResponse response = getBookHandler.handle(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    @Operation(summary = "Create a new book")
    public ResponseEntity<ApiResponse<BookResponse>> create(
            @AuthenticationPrincipal CurrentUser currentUser,
            @Valid @RequestBody CreateBookCommand command) {
        BookResponse response = createBookHandler.handle(currentUser.id(), command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @PostMapping("/{id}/archive")
    @Operation(summary = "Archive a book")
    public ResponseEntity<ApiResponse<BookResponse>> archive(@PathVariable UUID id) {
        BookResponse response = archiveBookHandler.handle(new ArchiveBookCommand(id));
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{id}/restore")
    @Operation(summary = "Restore an archived book")
    public ResponseEntity<ApiResponse<BookResponse>> restore(@PathVariable UUID id) {
        BookResponse response = restoreBookHandler.handle(new RestoreBookCommand(id));
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book (soft)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        deleteBookHandler.handle(new DeleteBookCommand(id));
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}