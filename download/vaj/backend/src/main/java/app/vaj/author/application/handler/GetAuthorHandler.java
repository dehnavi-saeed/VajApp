package app.vaj.author.application.handler;

import app.vaj.author.application.dto.AuthorResponse;
import app.vaj.author.domain.Author;
import app.vaj.author.domain.repository.AuthorRepository;
import app.vaj.author.infrastructure.mapper.AuthorMapper;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GetAuthorHandler {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public GetAuthorHandler(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Transactional(readOnly = true)
    public AuthorResponse handle(UUID id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author", id));
        return authorMapper.toResponse(author);
    }
}