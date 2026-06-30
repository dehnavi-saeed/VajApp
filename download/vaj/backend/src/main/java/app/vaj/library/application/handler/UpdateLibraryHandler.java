package app.vaj.library.application.handler;

import app.vaj.library.application.command.UpdateLibraryCommand;
import app.vaj.library.application.dto.LibraryResponse;
import app.vaj.library.domain.Library;
import app.vaj.library.domain.repository.LibraryRepository;
import app.vaj.library.infrastructure.mapper.LibraryMapper;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class UpdateLibraryHandler {

    private final LibraryRepository libraryRepository;
    private final LibraryMapper libraryMapper;
    private final Clock clock;

    public UpdateLibraryHandler(LibraryRepository libraryRepository, LibraryMapper libraryMapper, Clock clock) {
        this.libraryRepository = libraryRepository;
        this.libraryMapper = libraryMapper;
        this.clock = clock;
    }

    @Transactional
    public LibraryResponse handle(UUID userId, UUID libraryId, UpdateLibraryCommand command) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("Library", libraryId));

        if (command.name() != null && !command.name().isBlank()) {
            library.rename(command.name(), clock);
        }
        if (command.description() != null) {
            library.updateDescription(command.description(), clock);
        }

        libraryRepository.save(library);

        return libraryMapper.toResponse(library);
    }
}