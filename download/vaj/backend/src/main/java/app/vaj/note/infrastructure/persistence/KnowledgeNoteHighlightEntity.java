package app.vaj.note.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "KnowledgeNoteHighlights")
@IdClass(KnowledgeNoteHighlightEntity.NoteHighlightId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeNoteHighlightEntity {

    @Id
    @Column(name = "NoteId")
    private UUID noteId;

    @Id
    @Column(name = "HighlightId")
    private UUID highlightId;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoteHighlightId implements Serializable {
        private UUID noteId;
        private UUID highlightId;
    }
}