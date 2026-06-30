package app.vaj.publisher.application.handler;

import app.vaj.publisher.application.command.UpdatePublisherCommand;
import app.vaj.publisher.application.dto.PublisherResponse;
import app.vaj.publisher.domain.Publisher;
import app.vaj.publisher.domain.repository.PublisherRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdatePublisherHandler {

    private final PublisherRepository publisherRepository;
    private final java.time.Clock clock;

    public UpdatePublisherHandler(PublisherRepository publisherRepository, java.time.Clock clock) {
        this.publisherRepository = publisherRepository;
        this.clock = clock;
    }

    @Transactional
    public PublisherResponse handle(UpdatePublisherCommand command, UUID userId) {
        Publisher publisher = publisherRepository.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException("Publisher", command.id()));
        publisher.update(command.name(), command.website(), command.country(), userId, clock);
        publisherRepository.save(publisher);
        return new PublisherResponse(publisher.getId(), publisher.getName(),
                publisher.getWebsite(), publisher.getCountry(),
                publisher.getStatus().name(), publisher.getCreatedAt(), publisher.getUpdatedAt());
    }
}