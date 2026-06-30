package app.vaj.search.api;

import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import app.vaj.search.application.dto.SearchResponse;
import app.vaj.search.application.handler.SearchHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/search")
@Tag(name = "Search", description = "Full-text search")
public class SearchController {

    private final SearchHandler handler;

    public SearchController(SearchHandler handler) {
        this.handler = handler;
    }

    @GetMapping
    @Operation(summary = "Search across books, notes, and highlights")
    public ResponseEntity<ApiResponse<SearchResponse>> search(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        SearchResponse data = handler.handle(currentUser.id(), q, page, size);
        return ResponseEntity.ok(ApiResponse.success(data));
    }
}