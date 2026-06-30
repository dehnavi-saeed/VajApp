package app.vaj.user.api;

import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import app.vaj.user.application.command.UpdatePreferencesCommand;
import app.vaj.user.application.command.UpdateProfileCommand;
import app.vaj.user.application.dto.PreferencesResponse;
import app.vaj.user.application.dto.ProfileResponse;
import app.vaj.user.application.handler.UpdatePreferencesHandler;
import app.vaj.user.application.handler.UpdateProfileHandler;
import app.vaj.user.domain.UserPreferences;
import app.vaj.user.domain.repository.UserPreferencesRepository;
import app.vaj.user.domain.repository.UserProfileRepository;
import app.vaj.user.infrastructure.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User profile and preferences management")
public class UserController {

    private final UserProfileRepository profileRepository;
    private final UserPreferencesRepository preferencesRepository;
    private final UpdateProfileHandler updateProfileHandler;
    private final UpdatePreferencesHandler updatePreferencesHandler;
    private final UserMapper userMapper;

    public UserController(UserProfileRepository profileRepository,
                          UserPreferencesRepository preferencesRepository,
                          UpdateProfileHandler updateProfileHandler,
                          UpdatePreferencesHandler updatePreferencesHandler,
                          UserMapper userMapper) {
        this.profileRepository = profileRepository;
        this.preferencesRepository = preferencesRepository;
        this.updateProfileHandler = updateProfileHandler;
        this.updatePreferencesHandler = updatePreferencesHandler;
        this.userMapper = userMapper;
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(
            @AuthenticationPrincipal CurrentUser currentUser) {
        return profileRepository.findByUserId(currentUser.id())
                .map(profile -> ResponseEntity.ok(ApiResponse.success(userMapper.toResponse(profile))))
                .orElseGet(() -> ResponseEntity.ok(ApiResponse.success(null)));
    }

    @PatchMapping("/me")
    @Operation(summary = "Update current user profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(
            @AuthenticationPrincipal CurrentUser currentUser,
            @Valid @RequestBody UpdateProfileCommand command) {
        ProfileResponse response = updateProfileHandler.handle(currentUser.id(), command);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/me/preferences")
    @Operation(summary = "Get current user preferences")
    public ResponseEntity<ApiResponse<PreferencesResponse>> getPreferences(
            @AuthenticationPrincipal CurrentUser currentUser) {
        UserPreferences prefs = preferencesRepository.findByUserId(currentUser.id())
                .orElseGet(() -> {
                    UserPreferences defaults = UserPreferences.create(
                            UUID.randomUUID(), currentUser.id(), "UTC");
                    return preferencesRepository.save(defaults);
                });
        return ResponseEntity.ok(ApiResponse.success(userMapper.toResponse(prefs)));
    }

    @PatchMapping("/me/preferences")
    @Operation(summary = "Update current user preferences")
    public ResponseEntity<ApiResponse<PreferencesResponse>> updatePreferences(
            @AuthenticationPrincipal CurrentUser currentUser,
            @Valid @RequestBody UpdatePreferencesCommand command) {
        PreferencesResponse response = updatePreferencesHandler.handle(currentUser.id(), command);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}