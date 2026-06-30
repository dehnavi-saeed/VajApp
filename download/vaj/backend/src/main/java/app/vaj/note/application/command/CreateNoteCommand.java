package app.vaj.note.application.command;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;
public record CreateNoteCommand(
    @Size(max = 300) String title, String content, List<UUID> bookIds, List<UUID> highlightIds
) {}