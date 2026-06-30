package app.vaj.dashboard.api;

import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard", description = "User dashboard")
public class DashboardController {

    @GetMapping
    @Operation(summary = "Get user dashboard data")
    public ResponseEntity<ApiResponse<Object>> get(@AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "activeGoals", java.util.List.of(), "recentlyRead", java.util.List.of(),
                "currentlyReading", java.util.List.of(), "readingStreak", 0
        )));
    }
}