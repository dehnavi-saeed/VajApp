package app.vaj.collection.api;

import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/collections")
@Tag(name = "Collections", description = "Collection management")
public class CollectionController {

    @GetMapping
    @Operation(summary = "List user collections")
    public ResponseEntity<ApiResponse<Object>> list(
            @AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("collections", java.util.List.of())));
    }

    @PostMapping
    @Operation(summary = "Create a collection")
    public ResponseEntity<ApiResponse<Map<String, UUID>>> create(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestBody Map<String, Object> body) {
        UUID id = UUID.randomUUID();
        return ResponseEntity.ok(ApiResponse.success(Map.of("id", id)));
    }

    @PostMapping("/{id}/books/{bookId}")
    @Operation(summary = "Add book to collection")
    public ResponseEntity<ApiResponse<Void>> addBook(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id,
            @PathVariable UUID bookId) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/{id}/books/{bookId}")
    @Operation(summary = "Remove book from collection")
    public ResponseEntity<ApiResponse<Void>> removeBook(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id,
            @PathVariable UUID bookId) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete collection")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}