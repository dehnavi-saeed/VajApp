package app.vaj.library.api;

import app.vaj.library.application.command.*;
import app.vaj.library.application.dto.LibraryDetailResponse;
import app.vaj.library.application.dto.LibraryResponse;
import app.vaj.library.application.handler.*;
import app.vaj.library.application.query.GetLibraryQuery;
import app.vaj.library.application.query.ListLibrariesQuery;
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
@RequestMapping("/api/v1/libraries")
@Tag(name = "Libraries", description = "Library management")
public class LibraryController {

    private final CreateLibraryHandler createLibraryHandler;
    private final GetLibraryHandler getLibraryHandler;
    private final ListLibrariesHandler listLibrariesHandler;
    private final UpdateLibraryHandler updateLibraryHandler;
    private final ArchiveLibraryHandler archiveLibraryHandler;
    private final RestoreLibraryHandler restoreLibraryHandler;
    private final DeleteLibraryHandler deleteLibraryHandler;

    public LibraryController(CreateLibraryHandler createLibraryHandler,
                             GetLibraryHandler getLibraryHandler,
                             ListLibrariesHandler listLibrariesHandler,
                             UpdateLibraryHandler updateLibraryHandler,
                             ArchiveLibraryHandler archiveLibraryHandler,
                             RestoreLibraryHandler restoreLibraryHandler,
                             DeleteLibraryHandler deleteLibraryHandler) {
        this.createLibraryHandler = createLibraryHandler;
        this.getLibraryHandler = getLibraryHandler;
        this.listLibrariesHandler = listLibrariesHandler;
        this.updateLibraryHandler = updateLibraryHandler;
        this.archiveLibraryHandler = archiveLibraryHandler;
        this.restoreLibraryHandler = restoreLibraryHandler;
        this.deleteLibraryHandler = deleteLibraryHandler;
    }

    @GetMapping
    @Operation(summary = "List user libraries")
    public ResponseEntity<ApiResponse<PaginatedResponse<LibraryResponse>>> list(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort) {
        ListLibrariesQuery query = new ListLibrariesQuery(page, size, sort);
        PaginatedResponse<LibraryResponse> result = listLibrariesHandler.handle(currentUser.id(), query);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get library by id")
    public ResponseEntity<ApiResponse<LibraryDetailResponse>> getById(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        GetLibraryQuery query = new GetLibraryQuery(id);
        LibraryDetailResponse response = getLibraryHandler.handle(currentUser.id(), query);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    @Operation(summary = "Create a new library")
    public ResponseEntity<ApiResponse<LibraryResponse>> create(
            @AuthenticationPrincipal CurrentUser currentUser,
            @Valid @RequestBody CreateLibraryCommand command) {
        LibraryResponse response = createLibraryHandler.handle(currentUser.id(), command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update library")
    public ResponseEntity<ApiResponse<LibraryResponse>> update(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id,
            @Valid @RequestBody UpdateLibraryCommand command) {
        LibraryResponse response = updateLibraryHandler.handle(currentUser.id(), id, command);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{id}/archive")
    @Operation(summary = "Archive library")
    public ResponseEntity<ApiResponse<LibraryResponse>> archive(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        ArchiveLibraryCommand command = new ArchiveLibraryCommand(id);
        LibraryResponse response = archiveLibraryHandler.handle(currentUser.id(), command);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{id}/restore")
    @Operation(summary = "Restore library")
    public ResponseEntity<ApiResponse<LibraryResponse>> restore(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        RestoreLibraryCommand command = new RestoreLibraryCommand(id);
        LibraryResponse response = restoreLibraryHandler.handle(currentUser.id(), command);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete library")
    public ResponseEntity<ApiResponse<LibraryResponse>> delete(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        DeleteLibraryCommand command = new DeleteLibraryCommand(id);
        LibraryResponse response = deleteLibraryHandler.handle(currentUser.id(), command);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}