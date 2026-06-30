package app.vaj.author.application.handler;

import app.vaj.author.application.command.UpdateAuthorCommand;
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
public class UpdateAuthorHandler {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final Clock clock;

    public UpdateAuthorHandler(AuthorRepository authorRepository, AuthorMapper authorMapper, Clock clock) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.clock = clock;
    }

    @Transactional
    public AuthorResponse handle(UUID userId, UpdateAuthorCommand command) {
        Author author = authorRepository.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException("Author", command.id()));

        author.update(command.name(), command.bio(), userId, clock);
        authorRepository.save(author);

        return authorMapper.toResponse(author);
    }
}