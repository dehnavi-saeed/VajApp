package app.vaj.bookmark.api;

import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bookmarks")
@Tag(name = "Bookmarks", description = "Bookmark management")
public class BookmarkController {

    @GetMapping("/book/{bookId}")
    @Operation(summary = "Get bookmarks for a book")
    public ResponseEntity<ApiResponse<Object>> listByBook(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID bookId) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("bookmarks", java.util.List.of())));
    }

    @PostMapping
    @Operation(summary = "Create a bookmark")
    public ResponseEntity<ApiResponse<Map<String, UUID>>> create(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("id", UUID.randomUUID())));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a bookmark")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}