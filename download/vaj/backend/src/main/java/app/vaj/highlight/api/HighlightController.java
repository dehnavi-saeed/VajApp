package app.vaj.highlight.api;

import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/highlights")
@Tag(name = "Highlights", description = "Highlight management")
public class HighlightController {

    @GetMapping("/book/{bookId}")
    @Operation(summary = "Get highlights for a book")
    public ResponseEntity<ApiResponse<Object>> listByBook(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID bookId) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("highlights", java.util.List.of())));
    }

    @PostMapping
    @Operation(summary = "Create a highlight")
    public ResponseEntity<ApiResponse<Map<String, UUID>>> create(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestBody Map<String, Object> body) {
        UUID id = UUID.randomUUID();
        return ResponseEntity.ok(ApiResponse.success(Map.of("id", id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a highlight")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}