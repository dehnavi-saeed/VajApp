package app.vaj.tag.application.command;
import jakarta.validation.constraints.Size;
public record UpdateTagCommand(@Size(min=2,max=100) String name, String color, @Size(max=500) String description) {}