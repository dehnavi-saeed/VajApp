package app.vaj.goal.api;

import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/goals")
@Tag(name = "Reading Goals", description = "Reading goal management")
public class GoalController {

    @GetMapping
    @Operation(summary = "List user reading goals")
    public ResponseEntity<ApiResponse<Object>> list(@AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("goals", java.util.List.of())));
    }

    @PostMapping
    @Operation(summary = "Create a reading goal")
    public ResponseEntity<ApiResponse<Map<String, UUID>>> create(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestBody Map<String, Object> body) {
        UUID id = UUID.randomUUID();
        return ResponseEntity.ok(ApiResponse.success(Map.of("id", id)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get goal details")
    public ResponseEntity<ApiResponse<Object>> get(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("id", id, "progress", 0)));
    }

    @PatchMapping("/{id}/progress")
    @Operation(summary = "Update goal progress")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateProgress(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id,
            @RequestBody Map<String, Integer> body) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("updated", true)));
    }

    @PostMapping("/{id}/abandon")
    @Operation(summary = "Abandon a goal")
    public ResponseEntity<ApiResponse<Void>> abandon(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}