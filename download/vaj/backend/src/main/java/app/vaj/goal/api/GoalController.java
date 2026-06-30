package app.vaj.goal.api;

import app.vaj.goal.application.command.CreateGoalCommand;
import app.vaj.goal.application.command.UpdateGoalCommand;
import app.vaj.goal.application.dto.ReadingGoalResponse;
import app.vaj.goal.application.handler.*;
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
@RequestMapping("/api/v1/reading-goals")
@Tag(name = "Reading Goals", description = "Reading goal management")
public class GoalController {
    private final CreateGoalHandler createHandler;
    private final UpdateGoalHandler updateHandler;
    private final PauseGoalHandler pauseHandler;
    private final ResumeGoalHandler resumeHandler;
    private final CancelGoalHandler cancelHandler;
    private final ListGoalsHandler listHandler;

    public GoalController(CreateGoalHandler createHandler,
                          UpdateGoalHandler updateHandler,
                          PauseGoalHandler pauseHandler,
                          ResumeGoalHandler resumeHandler,
                          CancelGoalHandler cancelHandler,
                          ListGoalsHandler listHandler) {
        this.createHandler = createHandler;
        this.updateHandler = updateHandler;
        this.pauseHandler = pauseHandler;
        this.resumeHandler = resumeHandler;
        this.cancelHandler = cancelHandler;
        this.listHandler = listHandler;
    }

    @GetMapping
    @Operation(summary = "List reading goals")
    public ResponseEntity<ApiResponse<List<ReadingGoalResponse>>> list(@AuthenticationPrincipal CurrentUser user) {
        return ResponseEntity.ok(ApiResponse.success(listHandler.handle(user.id())));
    }

    @PostMapping
    @Operation(summary = "Create reading goal")
    public ResponseEntity<ApiResponse<ReadingGoalResponse>> create(
            @AuthenticationPrincipal CurrentUser user, @Valid @RequestBody CreateGoalCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(createHandler.handle(user.id(), cmd)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update reading goal")
    public ResponseEntity<ApiResponse<ReadingGoalResponse>> update(
            @PathVariable UUID id, @Valid @RequestBody UpdateGoalCommand cmd) {
        return ResponseEntity.ok(ApiResponse.success(updateHandler.handle(id, cmd)));
    }

    @PostMapping("/{id}/pause")
    @Operation(summary = "Pause reading goal")
    public ResponseEntity<ApiResponse<ReadingGoalResponse>> pause(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(pauseHandler.handle(id)));
    }

    @PostMapping("/{id}/resume")
    @Operation(summary = "Resume reading goal")
    public ResponseEntity<ApiResponse<ReadingGoalResponse>> resume(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(resumeHandler.handle(id)));
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel reading goal")
    public ResponseEntity<ApiResponse<Void>> cancel(@PathVariable UUID id) {
        cancelHandler.handle(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}