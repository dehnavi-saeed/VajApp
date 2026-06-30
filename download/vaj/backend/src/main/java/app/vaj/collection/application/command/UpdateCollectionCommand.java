package app.vaj.collection.application.command;
import jakarta.validation.constraints.Size;
public record UpdateCollectionCommand(@Size(min=3,max=200) String name, @Size(max=1000) String description) {}