package app.vaj.user.api;

import app.vaj.user.application.command.UpdatePreferencesCommand;
import app.vaj.user.application.command.UpdateProfileCommand;
import app.vaj.user.application.dto.PreferencesResponse;
import app.vaj.user.application.dto.ProfileResponse;
import app.vaj.user.application.handler.UpdateProfileHandler;
import app.vaj.user.domain.UserPreferences;
import app.vaj.user.domain.UserProfile;
import app.vaj.user.domain.repository.UserPreferencesRepository;
import app.vaj.user.domain.repository.UserProfileRepository;
import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
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

    public UserController(UserProfileRepository profileRepository,
                          UserPreferencesRepository preferencesRepository,
                          UpdateProfileHandler updateProfileHandler) {
        this.profileRepository = profileRepository;
        this.preferencesRepository = preferencesRepository;
        this.updateProfileHandler = updateProfileHandler;
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(@AuthenticationPrincipal CurrentUser currentUser) {
        return profileRepository.findByUserId(currentUser.id())
                .map(p -> ResponseEntity.ok(ApiResponse.success(
                        new ProfileResponse(p.getId(), p.getUserId(), p.getDisplayName(),
                                p.getBio(), p.getAvatarUrl(), p.getCreatedAt()))))
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
        return preferencesRepository.findByUserId(currentUser.id())
                .map(p -> ResponseEntity.ok(ApiResponse.success(
                        new PreferencesResponse(p.getId(), p.getUserId(), p.getLanguage(),
                                p.getTimezone(), p.getTheme()))))
                .orElseGet(() -> {
                    UserPreferences defaults = UserPreferences.create(UUID.randomUUID(), currentUser.id(), "UTC");
                    preferencesRepository.save(defaults);
                    return ResponseEntity.ok(ApiResponse.success(
                            new PreferencesResponse(defaults.getId(), defaults.getUserId(),
                                    defaults.getLanguage(), defaults.getTimezone(), defaults.getTheme())));
                });
    }

    @PatchMapping("/me/preferences")
    @Operation(summary = "Update current user preferences")
    public ResponseEntity<ApiResponse<PreferencesResponse>> updatePreferences(
            @AuthenticationPrincipal CurrentUser currentUser,
            @Valid @RequestBody UpdatePreferencesCommand command) {
        UserPreferences prefs = preferencesRepository.findByUserId(currentUser.id())
                .orElseGet(() -> UserPreferences.create(UUID.randomUUID(), currentUser.id(), command.timezone()));

        if (command.language() != null) prefs.updateLanguage(command.language());
        prefs.updateTimezone(command.timezone());
        if (command.theme() != null) prefs.updateTheme(command.theme());

        preferencesRepository.save(prefs);

        return ResponseEntity.ok(ApiResponse.success(
                new PreferencesResponse(prefs.getId(), prefs.getUserId(),
                        prefs.getLanguage(), prefs.getTimezone(), prefs.getTheme())));
    }

    @DeleteMapping("/me")
    @Operation(summary = "Delete current user account")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}