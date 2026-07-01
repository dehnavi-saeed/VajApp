package app.vaj.publisher.infrastructure.mapper;

import app.vaj.publisher.application.dto.PublisherResponse;
import app.vaj.publisher.domain.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface PublisherMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    PublisherResponse toResponse(Publisher publisher);

    @Named("statusToString")
    default String statusToString(app.vaj.publisher.domain.PublisherStatus status) {
        return status != null ? status.name() : null;
    }
}