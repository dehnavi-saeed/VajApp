package app.vaj.library.application.handler;

import app.vaj.library.application.command.CreateLibraryCommand;
import app.vaj.library.application.dto.LibraryResponse;
import app.vaj.library.domain.Library;
import app.vaj.library.domain.event.LibraryCreated;
import app.vaj.library.domain.repository.LibraryRepository;
import app.vaj.common.domain.DomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class CreateLibraryHandler {

    private final LibraryRepository libraryRepository;
    private final Clock clock;

    public CreateLibraryHandler(LibraryRepository libraryRepository, Clock clock) {
        this.libraryRepository = libraryRepository;
        this.clock = clock;
    }

    @Transactional
    public LibraryResponse handle(UUID userId, CreateLibraryCommand command) {
        if (libraryRepository.existsByUserIdAndName(userId, command.name())) {
            throw new DomainException("LIBRARY_NAME_EXISTS",
                    "A library with this name already exists.");
        }

        UUID libraryId = UUID.randomUUID();
        Library library = Library.create(libraryId, userId, command.name(), command.description(), clock);
        library.registerEvent(new LibraryCreated(UUID.randomUUID(), java.time.Instant.now(clock), libraryId, userId, command.name()));

        libraryRepository.save(library);

        return new LibraryResponse(
                library.getId(), library.getUserId(), library.getName(),
                library.getDescription(), library.getStatus().name(),
                library.getCreatedAt(), library.getUpdatedAt()
        );
    }
}