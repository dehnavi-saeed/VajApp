package app.vaj.collection.api;

import app.vaj.collection.application.command.*;
import app.vaj.collection.application.dto.CollectionResponse;
import app.vaj.collection.application.handler.*;
import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/collections")
@Tag(name = "Collections", description = "Collection management")
public class CollectionController {
    private final CreateCollectionHandler createHandler;
    private final UpdateCollectionHandler updateHandler;
    private final DeleteCollectionHandler deleteHandler;
    private final ListCollectionsHandler listHandler;

    public CollectionController(CreateCollectionHandler createHandler,
                                 UpdateCollectionHandler updateHandler,
                                 DeleteCollectionHandler deleteHandler,
                                 ListCollectionsHandler listHandler) {
        this.createHandler = createHandler;
        this.updateHandler = updateHandler;
        this.deleteHandler = deleteHandler;
        this.listHandler = listHandler;
    }

    @GetMapping
    @Operation(summary = "List collections")
    public ResponseEntity<ApiResponse<List<CollectionResponse>>> list(@AuthenticationPrincipal CurrentUser user) {
        return ResponseEntity.ok(ApiResponse.success(listHandler.handle(user.id())));
    }

    @PostMapping
    @Operation(summary = "Create a collection")
    public ResponseEntity<ApiResponse<CollectionResponse>> create(
            @AuthenticationPrincipal CurrentUser user, @Valid @RequestBody CreateCollectionCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(createHandler.handle(user.id(), cmd)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a collection")
    public ResponseEntity<ApiResponse<CollectionResponse>> update(
            @PathVariable UUID id, @Valid @RequestBody UpdateCollectionCommand cmd) {
        return ResponseEntity.ok(ApiResponse.success(updateHandler.handle(id, cmd)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a collection")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        deleteHandler.handle(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}