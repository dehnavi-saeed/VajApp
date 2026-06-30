package app.vaj.highlight.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaHighlightTagRepository extends JpaRepository<HighlightTagEntity, HighlightTagEntity.HighlightTagId> {
    List<HighlightTagEntity> findByHighlightId(UUID highlightId);

    void deleteByHighlightId(UUID highlightId);
}