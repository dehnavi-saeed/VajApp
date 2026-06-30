package app.vaj.note.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "KnowledgeNoteBooks")
@IdClass(KnowledgeNoteBookEntity.NoteBookId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeNoteBookEntity {

    @Id
    @Column(name = "NoteId")
    private UUID noteId;

    @Id
    @Column(name = "BookId")
    private UUID bookId;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoteBookId implements Serializable {
        private UUID noteId;
        private UUID bookId;
    }
}