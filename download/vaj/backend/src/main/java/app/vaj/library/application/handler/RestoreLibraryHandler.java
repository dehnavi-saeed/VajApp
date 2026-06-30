package app.vaj.library.application.handler;

import app.vaj.library.application.command.RestoreLibraryCommand;
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
public class RestoreLibraryHandler {

    private final LibraryRepository libraryRepository;
    private final LibraryMapper libraryMapper;
    private final Clock clock;

    public RestoreLibraryHandler(LibraryRepository libraryRepository, LibraryMapper libraryMapper, Clock clock) {
        this.libraryRepository = libraryRepository;
        this.libraryMapper = libraryMapper;
        this.clock = clock;
    }

    @Transactional
    public LibraryResponse handle(UUID userId, RestoreLibraryCommand command) {
        Library library = libraryRepository.findById(command.libraryId())
                .orElseThrow(() -> new EntityNotFoundException("Library", command.libraryId()));

        library.restore(clock);
        libraryRepository.save(library);

        return libraryMapper.toResponse(library);
    }
}