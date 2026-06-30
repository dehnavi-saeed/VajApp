package app.vaj.auth.application.handler;

import app.vaj.auth.application.command.LoginCommand;
import app.vaj.auth.application.dto.AuthResponse;
import app.vaj.auth.domain.UserAuth;
import app.vaj.auth.domain.event.UserLoggedIn;
import app.vaj.auth.domain.repository.UserAuthRepository;
import app.vaj.auth.infrastructure.persistence.JpaRefreshTokenRepository;
import app.vaj.auth.infrastructure.security.JwtService;
import app.vaj.common.domain.DomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Service
public class LoginHandler {

    private static final Logger log = LoggerFactory.getLogger(LoginHandler.class);

    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JpaRefreshTokenRepository jpaRefreshTokenRepository;
    private final Clock clock;

    public LoginHandler(UserAuthRepository userAuthRepository,
                        PasswordEncoder passwordEncoder,
                        JwtService jwtService,
                        JpaRefreshTokenRepository jpaRefreshTokenRepository,
                        Clock clock) {
        this.userAuthRepository = userAuthRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.jpaRefreshTokenRepository = jpaRefreshTokenRepository;
        this.clock = clock;
    }

    @Transactional
    public AuthResponse handle(LoginCommand command) {
        UserAuth user = userAuthRepository.findByEmail(command.usernameOrEmail())
                .or(() -> userAuthRepository.findByUsername(command.usernameOrEmail()))
                .orElseThrow(() -> new DomainException("INVALID_CREDENTIALS", "Invalid username or password."));

        if (!user.canLogin()) {
            throw new DomainException("ACCOUNT_INACTIVE", "Account is not active.");
        }

        if (!passwordEncoder.matches(command.password(), user.getPasswordHash())) {
            throw new DomainException("INVALID_CREDENTIALS", "Invalid username or password.");
        }

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getEmail(), user.getUsername());
        String refreshTokenValue = jwtService.generateRefreshToken();
        Instant refreshTokenExpiry = Instant.now(clock).plusMillis(jwtService.getRefreshTokenExpiration());

        jpaRefreshTokenRepository.saveRefreshToken(
                user.getId(), refreshTokenValue, refreshTokenExpiry, clock);

        user.registerEvent(new UserLoggedIn(UUID.randomUUID(), Instant.now(clock), user.getId()));

        log.info("User logged in: {}", user.getUsername());

        return new AuthResponse(
                accessToken,
                refreshTokenValue,
                Instant.now(clock).plusMillis(jwtService.getAccessTokenExpiration()),
                new AuthResponse.UserSummary(user.getId(), user.getEmail(), user.getUsername(), user.getStatus().name())
        );
    }
}