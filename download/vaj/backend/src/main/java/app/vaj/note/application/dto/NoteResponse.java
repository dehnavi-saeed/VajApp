package app.vaj.note.application.dto;
import java.time.Instant;
import java.util.UUID;
public record NoteResponse(UUID id, String title, String content, String status, Instant createdAt, Instant updatedAt) {}