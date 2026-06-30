package app.vaj.library.api;

import app.vaj.library.application.command.CreateLibraryCommand;
import app.vaj.library.application.command.RenameLibraryCommand;
import app.vaj.library.application.dto.LibraryResponse;
import app.vaj.library.application.handler.CreateLibraryHandler;
import app.vaj.library.application.handler.RenameLibraryHandler;
import app.vaj.library.domain.Library;
import app.vaj.library.domain.repository.LibraryRepository;
import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.application.dto.PaginatedResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/libraries")
@Tag(name = "Libraries", description = "Library management")
public class LibraryController {

    private final LibraryRepository libraryRepository;
    private final CreateLibraryHandler createLibraryHandler;
    private final RenameLibraryHandler renameLibraryHandler;

    public LibraryController(LibraryRepository libraryRepository,
                              CreateLibraryHandler createLibraryHandler,
                              RenameLibraryHandler renameLibraryHandler) {
        this.libraryRepository = libraryRepository;
        this.createLibraryHandler = createLibraryHandler;
        this.renameLibraryHandler = renameLibraryHandler;
    }

    @GetMapping
    @Operation(summary = "List user libraries")
    public ResponseEntity<ApiResponse<PaginatedResponse<LibraryResponse>>> list(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<Library> result = libraryRepository.findByUserId(currentUser.id(), PageRequest.of(page, size));
        var items = result.getContent().stream()
                .map(lib -> new LibraryResponse(lib.getId(), lib.getUserId(), lib.getName(),
                        lib.getDescription(), lib.getStatus().name(), lib.getCreatedAt(), lib.getUpdatedAt()))
                .toList();
        return ResponseEntity.ok(ApiResponse.success(
                PaginatedResponse.of(items, page, size, result.getTotalElements())));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get library by id")
    public ResponseEntity<ApiResponse<LibraryResponse>> getById(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        return libraryRepository.findById(id)
                .filter(lib -> lib.getUserId().equals(currentUser.id()))
                .map(lib -> ResponseEntity.ok(ApiResponse.success(
                        new LibraryResponse(lib.getId(), lib.getUserId(), lib.getName(),
                                lib.getDescription(), lib.getStatus().name(), lib.getCreatedAt(), lib.getUpdatedAt()))))
                .orElse(ResponseEntity.notFound().build());
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
    @Operation(summary = "Rename library")
    public ResponseEntity<ApiResponse<LibraryResponse>> rename(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id,
            @Valid @RequestBody RenameLibraryCommand command) {
        LibraryResponse response = renameLibraryHandler.handle(currentUser.id(), id, command);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{id}/archive")
    @Operation(summary = "Archive library")
    public ResponseEntity<ApiResponse<Void>> archive(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        return libraryRepository.findById(id)
                .filter(lib -> lib.getUserId().equals(currentUser.id()))
                .map(lib -> { lib.archive(java.time.Clock.systemUTC()); libraryRepository.save(lib); return ResponseEntity.ok(ApiResponse.<Void>success(null)); })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/restore")
    @Operation(summary = "Restore library")
    public ResponseEntity<ApiResponse<Void>> restore(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        return libraryRepository.findById(id)
                .filter(lib -> lib.getUserId().equals(currentUser.id()))
                .map(lib -> { lib.restore(java.time.Clock.systemUTC()); libraryRepository.save(lib); return ResponseEntity.ok(ApiResponse.<Void>success(null)); })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete library")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID id) {
        return libraryRepository.findById(id)
                .filter(lib -> lib.getUserId().equals(currentUser.id()))
                .map(lib -> { lib.delete(java.time.Clock.systemUTC()); libraryRepository.save(lib); return ResponseEntity.ok(ApiResponse.<Void>success(null)); })
                .orElse(ResponseEntity.notFound().build());
    }
}