package app.vaj.bookmark.api;

import app.vaj.bookmark.application.command.CreateBookmarkCommand;
import app.vaj.bookmark.application.command.UpdateBookmarkCommand;
import app.vaj.bookmark.application.dto.BookmarkResponse;
import app.vaj.bookmark.application.handler.*;
import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Bookmarks", description = "Bookmark management")
public class BookmarkController {

    private final CreateBookmarkHandler createHandler;
    private final UpdateBookmarkHandler updateHandler;
    private final DeleteBookmarkHandler deleteHandler;
    private final ListBookmarksHandler listHandler;

    public BookmarkController(CreateBookmarkHandler createHandler, UpdateBookmarkHandler updateHandler,
                              DeleteBookmarkHandler deleteHandler, ListBookmarksHandler listHandler) {
        this.createHandler = createHandler; this.updateHandler = updateHandler;
        this.deleteHandler = deleteHandler; this.listHandler = listHandler;
    }

    @GetMapping("/api/v1/books/{bookId}/bookmarks")
    @Operation(summary = "List bookmarks for a book")
    public ResponseEntity<ApiResponse<List<BookmarkResponse>>> list(@PathVariable UUID bookId) {
        return ResponseEntity.ok(ApiResponse.success(listHandler.handle(bookId)));
    }

    @PostMapping("/api/v1/books/{bookId}/bookmarks")
    @Operation(summary = "Create a bookmark")
    public ResponseEntity<ApiResponse<BookmarkResponse>> create(
            @AuthenticationPrincipal CurrentUser user, @PathVariable UUID bookId,
            @Valid @RequestBody CreateBookmarkCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(createHandler.handle(user.id(),
                new CreateBookmarkCommand(bookId, cmd.page(), cmd.title(), cmd.description(), cmd.color()))));
    }

    @PatchMapping("/api/v1/bookmarks/{id}")
    @Operation(summary = "Update a bookmark")
    public ResponseEntity<ApiResponse<BookmarkResponse>> update(@PathVariable UUID id, @Valid @RequestBody UpdateBookmarkCommand cmd) {
        return ResponseEntity.ok(ApiResponse.success(updateHandler.handle(id, cmd)));
    }

    @DeleteMapping("/api/v1/bookmarks/{id}")
    @Operation(summary = "Delete a bookmark")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        deleteHandler.handle(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}