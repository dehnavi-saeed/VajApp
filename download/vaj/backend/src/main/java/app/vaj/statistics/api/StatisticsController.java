package app.vaj.statistics.api;

import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import app.vaj.statistics.application.dto.StatisticsResponse;
import app.vaj.statistics.application.handler.GetStatisticsHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/statistics")
@Tag(name = "Statistics", description = "Reading statistics")
public class StatisticsController {

    private final GetStatisticsHandler handler;

    public StatisticsController(GetStatisticsHandler handler) {
        this.handler = handler;
    }

    @GetMapping
    @Operation(summary = "Get user reading statistics")
    public ResponseEntity<ApiResponse<StatisticsResponse>> get(@AuthenticationPrincipal CurrentUser currentUser) {
        StatisticsResponse data = handler.handle(currentUser.id());
        return ResponseEntity.ok(ApiResponse.success(data));
    }
}