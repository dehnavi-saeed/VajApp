package app.vaj.readingstate.api;

import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import app.vaj.readingstate.application.dto.ReadingStateResponse;
import app.vaj.readingstate.application.handler.GetReadingStateHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reading-states")
@Tag(name = "Reading States", description = "Reading state projections (read-only)")
public class ReadingStateController {

    private final GetReadingStateHandler handler;

    public ReadingStateController(GetReadingStateHandler handler) {
        this.handler = handler;
    }

    @GetMapping("/book/{bookId}")
    @Operation(summary = "Get reading state for a book")
    public ResponseEntity<ApiResponse<ReadingStateResponse>> getByBook(@PathVariable UUID bookId) {
        return ResponseEntity.ok(ApiResponse.success(handler.handle(bookId)));
    }

    @GetMapping
    @Operation(summary = "Get all reading states for current user")
    public ResponseEntity<ApiResponse<List<ReadingStateResponse>>> listByUser(
            @AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(ApiResponse.success(handler.handleByUser(currentUser.id())));
    }
}