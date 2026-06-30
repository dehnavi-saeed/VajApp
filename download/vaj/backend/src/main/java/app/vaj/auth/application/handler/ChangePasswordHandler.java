package app.vaj.auth.application.handler;

import app.vaj.auth.application.command.ChangePasswordCommand;
import app.vaj.auth.application.dto.AuthResponse;
import app.vaj.auth.application.dto.UserDto;
import app.vaj.auth.domain.UserAuth;
import app.vaj.auth.domain.repository.UserAuthRepository;
import app.vaj.auth.infrastructure.security.JwtService;
import app.vaj.common.domain.DomainException;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class ChangePasswordHandler {

    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final Clock clock;

    public ChangePasswordHandler(UserAuthRepository userAuthRepository,
                                 PasswordEncoder passwordEncoder,
                                 Clock clock) {
        this.userAuthRepository = userAuthRepository;
        this.passwordEncoder = passwordEncoder;
        this.clock = clock;
    }

    @Transactional
    public void handle(UUID userId, ChangePasswordCommand command) {
        UserAuth user = userAuthRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));

        if (!passwordEncoder.matches(command.currentPassword(), user.getPasswordHash())) {
            throw new DomainException("INVALID_CURRENT_PASSWORD", "Current password is incorrect.");
        }

        user.changePassword(passwordEncoder.encode(command.newPassword()), clock);
        userAuthRepository.save(user);
    }
}