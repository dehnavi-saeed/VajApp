package app.vaj.auth.application.handler;

import app.vaj.auth.application.dto.AuthResponse;
import app.vaj.auth.domain.UserAuth;
import app.vaj.auth.domain.repository.UserAuthRepository;
import app.vaj.auth.infrastructure.persistence.JpaRefreshTokenRepository;
import app.vaj.auth.infrastructure.security.JwtService;
import app.vaj.common.domain.DomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenHandler {

    private final JpaRefreshTokenRepository jpaRefreshTokenRepository;
    private final UserAuthRepository userAuthRepository;
    private final JwtService jwtService;
    private final Clock clock;

    public RefreshTokenHandler(JpaRefreshTokenRepository jpaRefreshTokenRepository,
                               UserAuthRepository userAuthRepository,
                               JwtService jwtService,
                               Clock clock) {
        this.jpaRefreshTokenRepository = jpaRefreshTokenRepository;
        this.userAuthRepository = userAuthRepository;
        this.jwtService = jwtService;
        this.clock = clock;
    }

    @Transactional
    public AuthResponse handle(String refreshTokenValue) {
        var refreshToken = jpaRefreshTokenRepository.findByToken(refreshTokenValue)
                .filter(t -> t.getExpiresAt().isAfter(Instant.now(clock)) && t.getRevokedAt() == null)
                .orElseThrow(() -> new DomainException("INVALID_REFRESH_TOKEN", "Refresh token is invalid or expired."));

        UserAuth user = userAuthRepository.findById(refreshToken.getUserId())
                .orElseThrow(() -> new DomainException("USER_NOT_FOUND", "User not found."));

        if (!user.canLogin()) {
            throw new DomainException("ACCOUNT_INACTIVE", "Account is not active.");
        }

        jpaRefreshTokenRepository.revokeToken(refreshToken.getId(), clock);

        String newAccessToken = jwtService.generateAccessToken(user.getId(), user.getEmail(), user.getUsername());
        String newRefreshToken = jwtService.generateRefreshToken();
        Instant newExpiry = Instant.now(clock).plusMillis(jwtService.getRefreshTokenExpiration());

        jpaRefreshTokenRepository.saveRefreshToken(user.getId(), newRefreshToken, newExpiry, clock);

        return new AuthResponse(
                newAccessToken,
                newRefreshToken,
                Instant.now(clock).plusMillis(jwtService.getAccessTokenExpiration()),
                new AuthResponse.UserSummary(user.getId(), user.getEmail(), user.getUsername(), user.getStatus().name())
        );
    }
}