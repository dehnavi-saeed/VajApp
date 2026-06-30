package app.vaj.publisher.api;

import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/publishers")
@Tag(name = "Publishers", description = "Publisher management")
public class PublisherController {

    @GetMapping
    @Operation(summary = "List publishers")
    public ResponseEntity<ApiResponse<Object>> list(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("publishers", java.util.List.of())));
    }

    @PostMapping
    @Operation(summary = "Create a publisher")
    public ResponseEntity<ApiResponse<Map<String, UUID>>> create(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestBody Map<String, Object> body) {
        UUID id = UUID.randomUUID();
        return ResponseEntity.ok(ApiResponse.success(Map.of("id", id)));
    }
}