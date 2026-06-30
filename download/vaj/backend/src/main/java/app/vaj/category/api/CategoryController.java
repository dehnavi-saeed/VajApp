package app.vaj.category.api;

import app.vaj.category.application.command.CreateCategoryCommand;
import app.vaj.category.application.command.UpdateCategoryCommand;
import app.vaj.category.application.dto.CategoryResponse;
import app.vaj.category.application.handler.*;
import app.vaj.common.application.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Categories", description = "System category management")
public class CategoryController {

    private final CreateCategoryHandler createHandler;
    private final UpdateCategoryHandler updateHandler;
    private final GetCategoryHandler getHandler;
    private final ActivateCategoryHandler activateHandler;
    private final DeactivateCategoryHandler deactivateHandler;
    private final DeleteCategoryHandler deleteHandler;

    public CategoryController(CreateCategoryHandler createHandler,
                              UpdateCategoryHandler updateHandler,
                              GetCategoryHandler getHandler,
                              ActivateCategoryHandler activateHandler,
                              DeactivateCategoryHandler deactivateHandler,
                              DeleteCategoryHandler deleteHandler) {
        this.createHandler = createHandler;
        this.updateHandler = updateHandler;
        this.getHandler = getHandler;
        this.activateHandler = activateHandler;
        this.deactivateHandler = deactivateHandler;
        this.deleteHandler = deleteHandler;
    }

    @GetMapping
    @Operation(summary = "List all active categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> list() {
        return ResponseEntity.ok(ApiResponse.success(getHandler.handleListAll()));
    }

    @GetMapping("/all")
    @Operation(summary = "List all categories including inactive")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> listAll() {
        return ResponseEntity.ok(ApiResponse.success(getHandler.handleListAllIncludingInactive()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by id")
    public ResponseEntity<ApiResponse<CategoryResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(getHandler.handle(id)));
    }

    @PostMapping
    @Operation(summary = "Create a new category (admin)")
    public ResponseEntity<ApiResponse<CategoryResponse>> create(@Valid @RequestBody CreateCategoryCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(createHandler.handle(cmd)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a category (admin)")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(
            @PathVariable UUID id, @Valid @RequestBody UpdateCategoryCommand cmd) {
        return ResponseEntity.ok(ApiResponse.success(updateHandler.handle(id, cmd)));
    }

    @PostMapping("/{id}/activate")
    @Operation(summary = "Activate a category (admin)")
    public ResponseEntity<ApiResponse<Void>> activate(@PathVariable UUID id) {
        activateHandler.handle(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate a category (admin)")
    public ResponseEntity<ApiResponse<Void>> deactivate(@PathVariable UUID id) {
        deactivateHandler.handle(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category (soft, admin)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        deleteHandler.handle(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}