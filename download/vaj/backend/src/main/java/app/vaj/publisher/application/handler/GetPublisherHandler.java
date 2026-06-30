package app.vaj.publisher.application.handler;

import app.vaj.publisher.application.dto.PublisherResponse;
import app.vaj.publisher.domain.Publisher;
import app.vaj.publisher.domain.repository.PublisherRepository;
import app.vaj.common.domain.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GetPublisherHandler {

    private final PublisherRepository publisherRepository;

    public GetPublisherHandler(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Transactional(readOnly = true)
    public PublisherResponse handle(UUID id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publisher", id));
        return new PublisherResponse(publisher.getId(), publisher.getName(),
                publisher.getWebsite(), publisher.getCountry(),
                publisher.getStatus().name(), publisher.getCreatedAt(), publisher.getUpdatedAt());
    }
}