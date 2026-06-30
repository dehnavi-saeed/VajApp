package app.vaj.statistics.api;

import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/statistics")
@Tag(name = "Statistics", description = "Reading statistics")
public class StatisticsController {

    @GetMapping
    @Operation(summary = "Get user reading statistics")
    public ResponseEntity<ApiResponse<Object>> get(@AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "totalBooksRead", 0, "totalPagesRead", 0, "totalMinutesRead", 0,
                "totalHighlights", 0, "totalNotes", 0, "currentStreak", 0, "averageDailyPages", 0.0
        )));
    }
}