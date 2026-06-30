package app.vaj.collection.application.command;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record CreateCollectionCommand(@NotBlank @Size(min=3,max=200) String name, @Size(max=1000) String description) {}