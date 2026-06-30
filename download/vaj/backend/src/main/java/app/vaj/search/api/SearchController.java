package app.vaj.search.api;

import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/search")
@Tag(name = "Search", description = "Full-text search")
public class SearchController {

    @GetMapping
    @Operation(summary = "Search across books, notes, and highlights")
    public ResponseEntity<ApiResponse<Object>> search(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("query", q, "results", java.util.List.of(), "totalItems", 0)));
    }
}