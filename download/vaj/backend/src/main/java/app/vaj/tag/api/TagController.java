package app.vaj.tag.api;

import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tags")
@Tag(name = "Tags", description = "Tag management")
public class TagController {

    @GetMapping
    @Operation(summary = "List user tags")
    public ResponseEntity<ApiResponse<Object>> list(@AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("tags", java.util.List.of())));
    }

    @PostMapping
    @Operation(summary = "Create a tag")
    public ResponseEntity<ApiResponse<Map<String, UUID>>> create(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestBody Map<String, Object> body) {
        UUID id = UUID.randomUUID();
        return ResponseEntity.ok(ApiResponse.success(Map.of("id", id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a tag")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}