package app.vaj.book.application.command;

import java.util.UUID;

public record RestoreBookCommand(UUID id) {}