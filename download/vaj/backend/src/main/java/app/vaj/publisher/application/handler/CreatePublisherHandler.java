package app.vaj.publisher.application.handler;

import app.vaj.publisher.application.command.CreatePublisherCommand;
import app.vaj.publisher.application.dto.PublisherResponse;
import app.vaj.publisher.domain.Publisher;
import app.vaj.publisher.domain.event.PublisherCreated;
import app.vaj.publisher.domain.repository.PublisherRepository;
import app.vaj.common.domain.DomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Service
public class CreatePublisherHandler {

    private final PublisherRepository publisherRepository;
    private final Clock clock;

    public CreatePublisherHandler(PublisherRepository publisherRepository, Clock clock) {
        this.publisherRepository = publisherRepository;
        this.clock = clock;
    }

    @Transactional
    public PublisherResponse handle(CreatePublisherCommand command) {
        if (publisherRepository.existsByName(command.name())) {
            throw new DomainException("PUBLISHER_NAME_EXISTS", "Publisher name already exists.");
        }

        Publisher publisher = Publisher.create(UUID.randomUUID(), command.name(),
                command.website(), command.country(), clock);
        publisher.registerEvent(new PublisherCreated(UUID.randomUUID(), Instant.now(clock),
                publisher.getId(), command.name()));

        publisherRepository.save(publisher);

        return new PublisherResponse(publisher.getId(), publisher.getName(),
                publisher.getWebsite(), publisher.getCountry(),
                publisher.getStatus().name(), publisher.getCreatedAt(), publisher.getUpdatedAt());
    }
}