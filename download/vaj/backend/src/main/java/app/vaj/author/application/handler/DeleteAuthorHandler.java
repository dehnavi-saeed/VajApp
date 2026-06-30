package app.vaj.author.application.handler;

import app.vaj.author.application.dto.AuthorResponse;
import app.vaj.author.domain.Author;
import app.vaj.author.domain.repository.AuthorRepository;
import app.vaj.author.infrastructure.mapper.AuthorMapper;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class DeleteAuthorHandler {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final Clock clock;

    public DeleteAuthorHandler(AuthorRepository authorRepository, AuthorMapper authorMapper, Clock clock) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.clock = clock;
    }

    @Transactional
    public AuthorResponse handle(UUID id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author", id));

        author.softDelete(clock);
        authorRepository.save(author);

        return authorMapper.toResponse(author);
    }
}