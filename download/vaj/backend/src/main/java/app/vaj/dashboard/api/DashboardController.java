package app.vaj.dashboard.api;

import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import app.vaj.dashboard.application.dto.DashboardResponse;
import app.vaj.dashboard.application.handler.GetDashboardHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard", description = "User dashboard")
public class DashboardController {

    private final GetDashboardHandler handler;

    public DashboardController(GetDashboardHandler handler) {
        this.handler = handler;
    }

    @GetMapping
    @Operation(summary = "Get user dashboard data")
    public ResponseEntity<ApiResponse<DashboardResponse>> get(@AuthenticationPrincipal CurrentUser currentUser) {
        DashboardResponse data = handler.handle(currentUser.id());
        return ResponseEntity.ok(ApiResponse.success(data));
    }
}