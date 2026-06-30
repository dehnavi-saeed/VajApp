package app.vaj.auth.api;

import app.vaj.auth.application.command.*;
import app.vaj.auth.application.dto.AuthResponse;
import app.vaj.auth.application.dto.UserDto;
import app.vaj.auth.application.handler.*;
import app.vaj.common.application.dto.ApiResponse;
import app.vaj.common.infrastructure.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication and session management")
public class AuthController {

    private final RegisterHandler registerHandler;
    private final LoginHandler loginHandler;
    private final LogoutHandler logoutHandler;
    private final RefreshTokenHandler refreshTokenHandler;
    private final ChangePasswordHandler changePasswordHandler;

    public AuthController(RegisterHandler registerHandler,
                          LoginHandler loginHandler,
                          LogoutHandler logoutHandler,
                          RefreshTokenHandler refreshTokenHandler,
                          ChangePasswordHandler changePasswordHandler) {
        this.registerHandler = registerHandler;
        this.loginHandler = loginHandler;
        this.logoutHandler = logoutHandler;
        this.refreshTokenHandler = refreshTokenHandler;
        this.changePasswordHandler = changePasswordHandler;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterCommand command) {
        AuthResponse response = registerHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @PostMapping("/login")
    @Operation(summary = "Login with credentials")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginCommand command) {
        AuthResponse response = loginHandler.handle(command);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout current session")
    public ResponseEntity<ApiResponse<Void>> logout(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestHeader(value = "Refresh-Token", required = false) String refreshToken) {
        if (refreshToken != null) {
            logoutHandler.handle(currentUser.id(), refreshToken);
        }
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(
            @RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        AuthResponse response = refreshTokenHandler.handle(refreshToken);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/change-password")
    @Operation(summary = "Change password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @AuthenticationPrincipal CurrentUser currentUser,
            @Valid @RequestBody ChangePasswordCommand command) {
        changePasswordHandler.handle(currentUser.id(), command);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Request password reset")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordCommand command) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset password with token")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@Valid @RequestBody ResetPasswordCommand command) {
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current authenticated user info")
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser(@AuthenticationPrincipal CurrentUser currentUser) {
        UserDto user = new UserDto(currentUser.id(), currentUser.email(), currentUser.username(), "ACTIVE");
        return ResponseEntity.ok(ApiResponse.success(user));
    }
}