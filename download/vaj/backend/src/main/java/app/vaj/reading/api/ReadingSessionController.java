package app.vaj.reading.api;

import app.vaj.reading.application.command.*;
import app.vaj.reading.application.dto.ReadingSessionResponse;
import app.vaj.reading.application.handler.*;
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
@RequestMapping("/api/v1/reading-sessions")
@Tag(name = "Reading Sessions", description = "Reading session management")
public class ReadingSessionController {

    private final StartReadingHandler startHandler;
    private final PauseReadingHandler pauseHandler;
    private final FinishReadingHandler finishHandler;
    private final ListReadingSessionsHandler listHandler;

    public ReadingSessionController(StartReadingHandler startHandler, PauseReadingHandler pauseHandler,
                                    FinishReadingHandler finishHandler, ListReadingSessionsHandler listHandler) {
        this.startHandler = startHandler;
        this.pauseHandler = pauseHandler;
        this.finishHandler = finishHandler;
        this.listHandler = listHandler;
    }

    @PostMapping
    @Operation(summary = "Start a reading session")
    public ResponseEntity<ApiResponse<ReadingSessionResponse>> start(
            @AuthenticationPrincipal CurrentUser user, @Valid @RequestBody StartReadingCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(startHandler.handle(user.id(), cmd)));
    }

    @PostMapping("/{id}/pause")
    @Operation(summary = "Pause reading session")
    public ResponseEntity<ApiResponse<ReadingSessionResponse>> pause(
            @PathVariable UUID id, @Valid @RequestBody PauseReadingCommand cmd) {
        return ResponseEntity.ok(ApiResponse.success(pauseHandler.handle(id, cmd)));
    }

    @PostMapping("/{id}/finish")
    @Operation(summary = "Finish reading session")
    public ResponseEntity<ApiResponse<ReadingSessionResponse>> finish(
            @PathVariable UUID id, @Valid @RequestBody FinishReadingCommand cmd) {
        return ResponseEntity.ok(ApiResponse.success(finishHandler.handle(id, cmd)));
    }

    @GetMapping
    @Operation(summary = "List reading sessions for a book")
    public ResponseEntity<ApiResponse<List<ReadingSessionResponse>>> list(@RequestParam UUID bookId) {
        return ResponseEntity.ok(ApiResponse.success(listHandler.handle(bookId)));
    }
}