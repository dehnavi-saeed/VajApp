package app.vaj.library.application.handler;

import app.vaj.library.application.dto.LibraryDetailResponse;
import app.vaj.library.application.query.GetLibraryQuery;
import app.vaj.library.domain.Library;
import app.vaj.library.domain.repository.LibraryRepository;
import app.vaj.library.infrastructure.mapper.LibraryMapper;
import app.vaj.book.infrastructure.persistence.JpaBookRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetLibraryHandler {

    private final LibraryRepository libraryRepository;
    private final JpaBookRepository jpaBookRepository;
    private final LibraryMapper libraryMapper;

    public GetLibraryHandler(LibraryRepository libraryRepository, JpaBookRepository jpaBookRepository,
                             LibraryMapper libraryMapper) {
        this.libraryRepository = libraryRepository;
        this.jpaBookRepository = jpaBookRepository;
        this.libraryMapper = libraryMapper;
    }

    @Transactional(readOnly = true)
    public LibraryDetailResponse handle(UUID userId, GetLibraryQuery query) {
        Library library = libraryRepository.findById(query.libraryId())
                .orElseThrow(() -> new EntityNotFoundException("Library", query.libraryId()));

        long bookCount = jpaBookRepository.countByLibraryIdAndIsDeletedFalse(library.getId());

        return libraryMapper.toDetailResponse(library, bookCount);
    }
}