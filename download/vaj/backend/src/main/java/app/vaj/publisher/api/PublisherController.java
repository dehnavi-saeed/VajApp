package app.vaj.publisher.api;

import app.vaj.publisher.application.command.CreatePublisherCommand;
import app.vaj.publisher.application.command.UpdatePublisherCommand;
import app.vaj.publisher.application.dto.PublisherResponse;
import app.vaj.publisher.application.handler.*;
import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.application.dto.PaginatedResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/publishers")
@Tag(name = "Publishers", description = "Publisher management")
public class PublisherController {

    private final CreatePublisherHandler createPublisherHandler;
    private final UpdatePublisherHandler updatePublisherHandler;
    private final GetPublisherHandler getPublisherHandler;
    private final ListPublishersHandler listPublishersHandler;
    private final DeletePublisherHandler deletePublisherHandler;

    public PublisherController(CreatePublisherHandler createPublisherHandler,
                               UpdatePublisherHandler updatePublisherHandler,
                               GetPublisherHandler getPublisherHandler,
                               ListPublishersHandler listPublishersHandler,
                               DeletePublisherHandler deletePublisherHandler) {
        this.createPublisherHandler = createPublisherHandler;
        this.updatePublisherHandler = updatePublisherHandler;
        this.getPublisherHandler = getPublisherHandler;
        this.listPublishersHandler = listPublishersHandler;
        this.deletePublisherHandler = deletePublisherHandler;
    }

    @GetMapping
    @Operation(summary = "List publishers")
    public ResponseEntity<ApiResponse<PaginatedResponse<PublisherResponse>>> list(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PaginatedResponse<PublisherResponse> result = listPublishersHandler.handle(search, page, size);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get publisher by id")
    public ResponseEntity<ApiResponse<PublisherResponse>> getById(@PathVariable UUID id) {
        PublisherResponse response = getPublisherHandler.handle(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    @Operation(summary = "Create a new publisher")
    public ResponseEntity<ApiResponse<PublisherResponse>> create(
            @Valid @RequestBody CreatePublisherCommand command) {
        PublisherResponse response = createPublisherHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update publisher")
    public ResponseEntity<ApiResponse<PublisherResponse>> update(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePublisherCommand command) {
        PublisherResponse response = updatePublisherHandler.handle(
                new UpdatePublisherCommand(id, command.name(), command.website(), command.country()),
                currentUser.id());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete publisher (soft)")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        deletePublisherHandler.handle(id, currentUser.id());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}