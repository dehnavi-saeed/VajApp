package app.vaj.note.api;

import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notes")
@Tag(name = "Knowledge Notes", description = "Knowledge note management")
public class NoteController {

    @GetMapping("/book/{bookId}")
    @Operation(summary = "Get notes for a book")
    public ResponseEntity<ApiResponse<Object>> listByBook(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID bookId) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("notes", java.util.List.of())));
    }

    @PostMapping
    @Operation(summary = "Create a knowledge note")
    public ResponseEntity<ApiResponse<Map<String, UUID>>> create(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestBody Map<String, Object> body) {
        UUID id = UUID.randomUUID();
        return ResponseEntity.ok(ApiResponse.success(Map.of("id", id)));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update a knowledge note")
    public ResponseEntity<ApiResponse<Map<String, Object>>> update(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id,
            @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("updated", true)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a knowledge note")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}