package app.vaj.tag.api;

import app.vaj.tag.application.command.*;
import app.vaj.tag.application.dto.TagResponse;
import app.vaj.tag.application.handler.*;
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
@RequestMapping("/api/v1/tags")
@Tag(name = "Tags", description = "Tag management")
public class TagController {
    private final CreateTagHandler createHandler;
    private final UpdateTagHandler updateHandler;
    private final DeleteTagHandler deleteHandler;
    private final ListTagsHandler listHandler;

    public TagController(CreateTagHandler createHandler,
                         UpdateTagHandler updateHandler,
                         DeleteTagHandler deleteHandler,
                         ListTagsHandler listHandler) {
        this.createHandler = createHandler;
        this.updateHandler = updateHandler;
        this.deleteHandler = deleteHandler;
        this.listHandler = listHandler;
    }

    @GetMapping
    @Operation(summary = "List tags")
    public ResponseEntity<ApiResponse<List<TagResponse>>> list(@AuthenticationPrincipal CurrentUser user) {
        return ResponseEntity.ok(ApiResponse.success(listHandler.handle(user.id())));
    }

    @PostMapping
    @Operation(summary = "Create a tag")
    public ResponseEntity<ApiResponse<TagResponse>> create(
            @AuthenticationPrincipal CurrentUser user, @Valid @RequestBody CreateTagCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(createHandler.handle(user.id(), cmd)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a tag")
    public ResponseEntity<ApiResponse<TagResponse>> update(
            @PathVariable UUID id, @Valid @RequestBody UpdateTagCommand cmd) {
        return ResponseEntity.ok(ApiResponse.success(updateHandler.handle(id, cmd)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a tag")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        deleteHandler.handle(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}