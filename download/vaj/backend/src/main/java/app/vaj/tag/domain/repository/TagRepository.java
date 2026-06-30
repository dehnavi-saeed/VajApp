package app.vaj.tag.domain.repository;
import app.vaj.tag.domain.Tag;
import java.util.List;
import java.util.UUID;
public interface TagRepository {
    Tag save(Tag t);
    List<Tag> findByUserId(UUID userId);
    boolean existsByUserIdAndName(UUID userId, String name);
}