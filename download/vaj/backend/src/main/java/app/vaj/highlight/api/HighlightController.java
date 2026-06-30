package app.vaj.highlight.api;
import app.vaj.highlight.application.command.*;
import app.vaj.highlight.application.dto.HighlightResponse;
import app.vaj.highlight.application.handler.*;
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
@Tag(name = "Highlights", description = "Highlight management")
public class HighlightController {
    private final CreateHighlightHandler createHandler;
    private final UpdateHighlightHandler updateHandler;
    private final DeleteHighlightHandler deleteHandler;
    private final ListHighlightsHandler listHandler;

    public HighlightController(CreateHighlightHandler createHandler, UpdateHighlightHandler updateHandler,
                               DeleteHighlightHandler deleteHandler, ListHighlightsHandler listHandler) {
        this.createHandler = createHandler; this.updateHandler = updateHandler;
        this.deleteHandler = deleteHandler; this.listHandler = listHandler;
    }

    @GetMapping("/api/v1/books/{bookId}/highlights")
    @Operation(summary = "List highlights for a book")
    public ResponseEntity<ApiResponse<List<HighlightResponse>>> list(@PathVariable UUID bookId) {
        return ResponseEntity.ok(ApiResponse.success(listHandler.handle(bookId)));
    }

    @PostMapping("/api/v1/books/{bookId}/highlights")
    @Operation(summary = "Create a highlight")
    public ResponseEntity<ApiResponse<HighlightResponse>> create(
            @AuthenticationPrincipal CurrentUser user, @PathVariable UUID bookId,
            @Valid @RequestBody CreateHighlightCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(createHandler.handle(user.id(),
                new CreateHighlightCommand(bookId, cmd.page(), cmd.startPosition(), cmd.endPosition(), cmd.textSnapshot(), cmd.color(), cmd.comment()))));
    }

    @PatchMapping("/api/v1/highlights/{id}")
    @Operation(summary = "Update a highlight")
    public ResponseEntity<ApiResponse<HighlightResponse>> update(@PathVariable UUID id, @Valid @RequestBody UpdateHighlightCommand cmd) {
        return ResponseEntity.ok(ApiResponse.success(updateHandler.handle(id, cmd)));
    }

    @DeleteMapping("/api/v1/highlights/{id}")
    @Operation(summary = "Delete a highlight")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        deleteHandler.handle(id); return ResponseEntity.ok(ApiResponse.success(null));
    }
}