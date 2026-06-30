package app.vaj.highlight.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "HighlightTags")
@IdClass(HighlightTagEntity.HighlightTagId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HighlightTagEntity {

    @Id
    @Column(name = "HighlightId")
    private UUID highlightId;

    @Id
    @Column(name = "TagId")
    private UUID tagId;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HighlightTagId implements Serializable {
        private UUID highlightId;
        private UUID tagId;
    }
}