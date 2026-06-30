package app.vaj.author.api;

import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/authors")
@Tag(name = "Authors", description = "Author management")
public class AuthorController {

    @GetMapping
    @Operation(summary = "List authors")
    public ResponseEntity<ApiResponse<Object>> list(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("authors", java.util.List.of())));
    }

    @PostMapping
    @Operation(summary = "Create an author")
    public ResponseEntity<ApiResponse<Map<String, UUID>>> create(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestBody Map<String, Object> body) {
        UUID id = UUID.randomUUID();
        return ResponseEntity.ok(ApiResponse.success(Map.of("id", id)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get author by id")
    public ResponseEntity<ApiResponse<Object>> get(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("id", id, "name", "")));
    }
}