package app.vaj.library.application.handler;

import app.vaj.library.application.dto.LibraryResponse;
import app.vaj.library.application.query.ListLibrariesQuery;
import app.vaj.library.domain.Library;
import app.vaj.library.domain.repository.LibraryRepository;
import app.vaj.library.infrastructure.mapper.LibraryMapper;
import app.vaj.common.application.dto.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ListLibrariesHandler {

    private final LibraryRepository libraryRepository;
    private final LibraryMapper libraryMapper;

    public ListLibrariesHandler(LibraryRepository libraryRepository, LibraryMapper libraryMapper) {
        this.libraryRepository = libraryRepository;
        this.libraryMapper = libraryMapper;
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<LibraryResponse> handle(UUID userId, ListLibrariesQuery query) {
        Sort sort = parseSort(query.sort());
        PageRequest pageRequest = PageRequest.of(query.page(), query.size(), sort);

        Page<Library> result = libraryRepository.findByUserId(userId, pageRequest);
        List<LibraryResponse> items = result.getContent().stream()
                .map(libraryMapper::toResponse)
                .toList();

        return PaginatedResponse.of(items, query.page(), query.size(), result.getTotalElements());
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }
        String[] parts = sort.split(",");
        String property = parts[0].strip();
        Sort.Direction direction = parts.length > 1 && parts[1].strip().equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(direction, property);
    }
}