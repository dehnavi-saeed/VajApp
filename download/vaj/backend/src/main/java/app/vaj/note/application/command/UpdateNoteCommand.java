package app.vaj.note.application.command;
import jakarta.validation.constraints.Size;
public record UpdateNoteCommand(@Size(max = 300) String title, String content) {}