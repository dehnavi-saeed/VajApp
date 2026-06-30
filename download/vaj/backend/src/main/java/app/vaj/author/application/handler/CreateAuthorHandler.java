package app.vaj.author.application.handler;

import app.vaj.author.application.command.CreateAuthorCommand;
import app.vaj.author.application.dto.AuthorResponse;
import app.vaj.author.domain.Author;
import app.vaj.author.domain.event.AuthorCreated;
import app.vaj.author.domain.repository.AuthorRepository;
import app.vaj.author.infrastructure.mapper.AuthorMapper;
import app.vaj.common.domain.DomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Service
public class CreateAuthorHandler {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final Clock clock;

    public CreateAuthorHandler(AuthorRepository authorRepository, AuthorMapper authorMapper, Clock clock) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.clock = clock;
    }

    @Transactional
    public AuthorResponse handle(CreateAuthorCommand command) {
        if (authorRepository.existsByName(command.name())) {
            throw new DomainException("AUTHOR_NAME_EXISTS",
                    "An author with this name already exists.");
        }

        UUID authorId = UUID.randomUUID();
        Author author = Author.create(authorId, command.name(), command.bio(), command.type(), clock);
        author.registerEvent(new AuthorCreated(UUID.randomUUID(), Instant.now(clock), authorId, command.name()));

        authorRepository.save(author);

        return authorMapper.toResponse(author);
    }
}