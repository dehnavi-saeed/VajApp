package app.vaj.auth.domain.repository;

import app.vaj.auth.domain.UserAuth;

import java.util.Optional;
import java.util.UUID;

public interface UserAuthRepository {

    UserAuth save(UserAuth userAuth);

    Optional<UserAuth> findById(UUID id);

    Optional<UserAuth> findByEmail(String email);

    Optional<UserAuth> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}