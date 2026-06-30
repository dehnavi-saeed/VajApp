package app.vaj.author.api;

import app.vaj.author.application.command.CreateAuthorCommand;
import app.vaj.author.application.command.UpdateAuthorCommand;
import app.vaj.author.application.dto.AuthorResponse;
import app.vaj.author.application.handler.*;
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
@RequestMapping("/api/v1/authors")
@Tag(name = "Authors", description = "Author management")
public class AuthorController {

    private final CreateAuthorHandler createAuthorHandler;
    private final GetAuthorHandler getAuthorHandler;
    private final ListAuthorsHandler listAuthorsHandler;
    private final UpdateAuthorHandler updateAuthorHandler;
    private final DeleteAuthorHandler deleteAuthorHandler;

    public AuthorController(CreateAuthorHandler createAuthorHandler,
                            GetAuthorHandler getAuthorHandler,
                            ListAuthorsHandler listAuthorsHandler,
                            UpdateAuthorHandler updateAuthorHandler,
                            DeleteAuthorHandler deleteAuthorHandler) {
        this.createAuthorHandler = createAuthorHandler;
        this.getAuthorHandler = getAuthorHandler;
        this.listAuthorsHandler = listAuthorsHandler;
        this.updateAuthorHandler = updateAuthorHandler;
        this.deleteAuthorHandler = deleteAuthorHandler;
    }

    @GetMapping
    @Operation(summary = "List authors")
    public ResponseEntity<ApiResponse<PaginatedResponse<AuthorResponse>>> list(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {
        PaginatedResponse<AuthorResponse> result = listAuthorsHandler.handle(page, size, search);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get author by id")
    public ResponseEntity<ApiResponse<AuthorResponse>> get(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        AuthorResponse response = getAuthorHandler.handle(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    @Operation(summary = "Create an author")
    public ResponseEntity<ApiResponse<AuthorResponse>> create(
            @AuthenticationPrincipal CurrentUser currentUser,
            @Valid @RequestBody CreateAuthorCommand command) {
        AuthorResponse response = createAuthorHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update author")
    public ResponseEntity<ApiResponse<AuthorResponse>> update(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAuthorCommand command) {
        UpdateAuthorCommand merged = new UpdateAuthorCommand(id, command.name(), command.bio());
        AuthorResponse response = updateAuthorHandler.handle(currentUser.id(), merged);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete author")
    public ResponseEntity<ApiResponse<AuthorResponse>> delete(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        AuthorResponse response = deleteAuthorHandler.handle(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}