package app.vaj.publisher.application.handler;

import app.vaj.publisher.domain.Publisher;
import app.vaj.publisher.domain.repository.PublisherRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class DeletePublisherHandler {

    private final PublisherRepository publisherRepository;
    private final Clock clock;

    public DeletePublisherHandler(PublisherRepository publisherRepository, Clock clock) {
        this.publisherRepository = publisherRepository;
        this.clock = clock;
    }

    @Transactional
    public void handle(UUID id, UUID userId) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publisher", id));
        publisher.softDelete(userId, clock);
        publisherRepository.save(publisher);
    }
}