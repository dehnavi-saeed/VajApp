package app.vaj.note.api;
import app.vaj.note.application.command.*;
import app.vaj.note.application.dto.NoteResponse;
import app.vaj.note.application.handler.*;
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
@RequestMapping("/api/v1/knowledge-notes")
@Tag(name = "Knowledge Notes", description = "Knowledge note management")
public class NoteController {
    private final CreateNoteHandler createHandler;
    private final UpdateNoteHandler updateHandler;
    private final DeleteNoteHandler deleteHandler;
    private final ListNotesHandler listHandler;

    public NoteController(CreateNoteHandler createHandler, UpdateNoteHandler updateHandler,
                         DeleteNoteHandler deleteHandler, ListNotesHandler listHandler) {
        this.createHandler = createHandler; this.updateHandler = updateHandler;
        this.deleteHandler = deleteHandler; this.listHandler = listHandler;
    }

    @GetMapping
    @Operation(summary = "List knowledge notes")
    public ResponseEntity<ApiResponse<List<NoteResponse>>> list(@AuthenticationPrincipal CurrentUser user) {
        return ResponseEntity.ok(ApiResponse.success(listHandler.handle(user.id())));
    }

    @PostMapping
    @Operation(summary = "Create a knowledge note")
    public ResponseEntity<ApiResponse<NoteResponse>> create(
            @AuthenticationPrincipal CurrentUser user, @Valid @RequestBody CreateNoteCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(createHandler.handle(user.id(), cmd)));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update a knowledge note")
    public ResponseEntity<ApiResponse<NoteResponse>> update(@PathVariable UUID id, @Valid @RequestBody UpdateNoteCommand cmd) {
        return ResponseEntity.ok(ApiResponse.success(updateHandler.handle(id, cmd)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a knowledge note")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        deleteHandler.handle(id); return ResponseEntity.ok(ApiResponse.success(null));
    }
}