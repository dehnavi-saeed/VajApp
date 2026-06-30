package app.vaj.auth.application.handler;

import app.vaj.auth.domain.repository.UserAuthRepository;
import app.vaj.auth.infrastructure.persistence.JpaRefreshTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class LogoutHandler {

    private static final Logger log = LoggerFactory.getLogger(LogoutHandler.class);

    private final JpaRefreshTokenRepository jpaRefreshTokenRepository;
    private final UserAuthRepository userAuthRepository;

    public LogoutHandler(JpaRefreshTokenRepository jpaRefreshTokenRepository,
                         UserAuthRepository userAuthRepository) {
        this.jpaRefreshTokenRepository = jpaRefreshTokenRepository;
        this.userAuthRepository = userAuthRepository;
    }

    @Transactional
    public void handle(UUID userId, String refreshTokenValue) {
        jpaRefreshTokenRepository.findByToken(refreshTokenValue)
                .ifPresent(token -> {
                    if (token.getUserId().equals(userId)) {
                        jpaRefreshTokenRepository.revokeToken(token.getId(), java.time.Clock.systemUTC());
                    }
                });
        log.info("User logged out: {}", userId);
    }
}