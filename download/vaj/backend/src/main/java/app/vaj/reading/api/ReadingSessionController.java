package app.vaj.reading.api;

import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reading-sessions")
@Tag(name = "Reading Sessions", description = "Reading session management")
public class ReadingSessionController {

    @PostMapping
    @Operation(summary = "Start a reading session")
    public ResponseEntity<ApiResponse<Map<String, UUID>>> start(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestBody Map<String, Object> body) {
        UUID sessionId = UUID.randomUUID();
        return ResponseEntity.ok(ApiResponse.success(Map.of("sessionId", sessionId)));
    }

    @PatchMapping("/{id}/end")
    @Operation(summary = "End a reading session")
    public ResponseEntity<ApiResponse<Map<String, Object>>> end(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id,
            @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("ended", true)));
    }

    @GetMapping("/book/{bookId}")
    @Operation(summary = "Get reading sessions for a book")
    public ResponseEntity<ApiResponse<Object>> listByBook(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID bookId) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("sessions", java.util.List.of())));
    }
}