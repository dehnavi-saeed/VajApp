package app.vaj.auth.application.handler;

import app.vaj.auth.application.command.RegisterCommand;
import app.vaj.auth.application.dto.AuthResponse;
import app.vaj.auth.domain.UserAuth;
import app.vaj.auth.domain.event.UserRegistered;
import app.vaj.auth.domain.repository.UserAuthRepository;
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
public class RegisterHandler {

    private static final Logger log = LoggerFactory.getLogger(RegisterHandler.class);

    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final Clock clock;

    public RegisterHandler(UserAuthRepository userAuthRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           Clock clock) {
        this.userAuthRepository = userAuthRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.clock = clock;
    }

    @Transactional
    public AuthResponse handle(RegisterCommand command) {
        if (userAuthRepository.existsByEmail(command.email())) {
            throw new DomainException("EMAIL_ALREADY_EXISTS", "Email is already registered.");
        }

        if (userAuthRepository.existsByUsername(command.username())) {
            throw new DomainException("USERNAME_ALREADY_EXISTS", "Username is already taken.");
        }

        String passwordHash = passwordEncoder.encode(command.password());
        UUID userId = UUID.randomUUID();

        UserAuth user = UserAuth.create(userId, command.username(), command.email(), passwordHash, clock);
        user.activate(clock);
        user.registerEvent(new UserRegistered(UUID.randomUUID(), Instant.now(clock), userId, command.email(), command.username()));

        userAuthRepository.save(user);

        String accessToken = jwtService.generateAccessToken(userId, command.email(), command.username());

        log.info("User registered: {}", command.username());

        return new AuthResponse(
                accessToken,
                null,
                Instant.now(clock).plusMillis(jwtService.getAccessTokenExpiration()),
                new AuthResponse.UserSummary(userId, command.email(), command.username(), user.getStatus().name())
        );
    }
}