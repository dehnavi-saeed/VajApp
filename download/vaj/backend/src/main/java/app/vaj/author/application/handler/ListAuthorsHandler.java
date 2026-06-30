package app.vaj.author.application.handler;

import app.vaj.author.application.dto.AuthorResponse;
import app.vaj.author.domain.Author;
import app.vaj.author.domain.repository.AuthorRepository;
import app.vaj.author.infrastructure.mapper.AuthorMapper;
import app.vaj.common.application.dto.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListAuthorsHandler {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public ListAuthorsHandler(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<AuthorResponse> handle(int page, int size, String search) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Author> result;
        if (search != null && !search.isBlank()) {
            result = authorRepository.findByNameContaining(search, pageRequest);
        } else {
            result = authorRepository.findAll(pageRequest);
        }

        List<AuthorResponse> items = result.getContent().stream()
                .map(authorMapper::toResponse)
                .toList();

        return PaginatedResponse.of(items, page, size, result.getTotalElements());
    }
}