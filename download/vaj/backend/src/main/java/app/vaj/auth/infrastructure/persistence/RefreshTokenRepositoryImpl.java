package app.vaj.auth.infrastructure.persistence;

import app.vaj.auth.domain.RefreshToken;
import app.vaj.auth.domain.repository.RefreshTokenRepository;
import app.vaj.auth.infrastructure.mapper.UserAuthMapper;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final JpaRefreshTokenRepository jpaRepository;
    private final UserAuthMapper userAuthMapper;
    private final EntityManager entityManager;

    public RefreshTokenRepositoryImpl(JpaRefreshTokenRepository jpaRepository,
                                      UserAuthMapper userAuthMapper,
                                      EntityManager entityManager) {
        this.jpaRepository = jpaRepository;
        this.userAuthMapper = userAuthMapper;
        this.entityManager = entityManager;
    }

    @Override
    public RefreshToken save(RefreshToken token) {
        RefreshTokenEntity entity = userAuthMapper.toEntity(token);
        RefreshTokenEntity saved = jpaRepository.save(entity);
        return userAuthMapper.toDomain(saved);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return jpaRepository.findByToken(token)
                .map(userAuthMapper::toDomain);
    }

    @Override
    @Transactional
    public void deleteByUserId(UUID userId) {
        jpaRepository.deleteAllByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteExpiredTokens() {
        entityManager.createQuery(
                        "DELETE FROM RefreshTokenEntity r WHERE r.expiresAt < :now")
                .setParameter("now", Instant.now())
                .executeUpdate();
    }
}