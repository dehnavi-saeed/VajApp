package app.vaj.tag.domain.repository;

import app.vaj.tag.domain.Tag;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TagRepository {
    Tag save(Tag t);
    Optional<Tag> findById(UUID id);
    List<Tag> findByUserId(UUID userId);
    boolean existsByUserIdAndName(UUID userId, String name);
}