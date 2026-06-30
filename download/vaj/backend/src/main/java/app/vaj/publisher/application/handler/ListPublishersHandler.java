package app.vaj.publisher.application.handler;

import app.vaj.publisher.application.dto.PublisherResponse;
import app.vaj.publisher.domain.Publisher;
import app.vaj.publisher.domain.repository.PublisherRepository;
import app.vaj.common.application.dto.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListPublishersHandler {

    private final PublisherRepository publisherRepository;

    public ListPublishersHandler(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<PublisherResponse> handle(String search, int page, int size) {
        if (size < 1) size = 20;
        if (size > 100) size = 100;
        if (page < 0) page = 0;

        PageRequest pageable = PageRequest.of(page, size);
        Page<Publisher> result = (search != null && !search.isBlank())
                ? publisherRepository.findByNameContaining(search, pageable)
                : publisherRepository.findAll(pageable);

        List<PublisherResponse> items = result.getContent().stream()
                .map(p -> new PublisherResponse(p.getId(), p.getName(), p.getWebsite(),
                        p.getCountry(), p.getStatus().name(), p.getCreatedAt(), p.getUpdatedAt()))
                .toList();

        return PaginatedResponse.of(items, page, size, result.getTotalElements());
    }
}