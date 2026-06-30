package app.vaj.library.application.handler;

import app.vaj.library.application.command.RenameLibraryCommand;
import app.vaj.library.application.dto.LibraryResponse;
import app.vaj.library.domain.Library;
import app.vaj.library.domain.repository.LibraryRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class RenameLibraryHandler {

    private final LibraryRepository libraryRepository;
    private final Clock clock;

    public RenameLibraryHandler(LibraryRepository libraryRepository, Clock clock) {
        this.libraryRepository = libraryRepository;
        this.clock = clock;
    }

    @Transactional
    public LibraryResponse handle(UUID userId, UUID libraryId, RenameLibraryCommand command) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("Library", libraryId));

        library.rename(command.name(), clock);
        libraryRepository.save(library);

        return new LibraryResponse(
                library.getId(), library.getUserId(), library.getName(),
                library.getDescription(), library.getStatus().name(),
                library.getCreatedAt(), library.getUpdatedAt()
        );
    }
}